package fr.nistro.edoriaskyblock.util;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class used to execute Discord Webhooks with low effort
 */
public class DiscordWebhookUtil {

	private final String url;
	private String content;
	private String username;
	private String avatarUrl;
	private boolean tts;
	private final List<EmbedObject> embeds = new ArrayList<EmbedObject>();

	/**
	 * Constructs a new DiscordWebhook instance
	 *
	 * @param url The webhook URL obtained in Discord
	 */
	public DiscordWebhookUtil(String url) {
		this.url = url;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public void setTts(boolean tts) {
		this.tts = tts;
	}

	public void addEmbed(EmbedObject embed) {
		this.embeds.add(embed);
	}

	public void execute() throws IOException {
		if ((this.content == null) && this.embeds.isEmpty()) {
			throw new IllegalArgumentException("Set content or add at least one EmbedObject");
		}

		final JSONObject json = new JSONObject();

		json.put("content", this.content);
		json.put("username", this.username);
		json.put("avatar_url", this.avatarUrl);
		json.put("tts", this.tts);

		if (!this.embeds.isEmpty()) {
			final List<JSONObject> embedObjects = new ArrayList<JSONObject>();

			for (final EmbedObject embed : this.embeds) {
				final JSONObject jsonEmbed = new JSONObject();

				jsonEmbed.put("title", embed.getTitle());
				jsonEmbed.put("description", embed.getDescription());
				jsonEmbed.put("url", embed.getUrl());

				if (embed.getColor() != null) {
					final Color color = embed.getColor();
					int rgb = color.getRed();
					rgb = (rgb << 8) + color.getGreen();
					rgb = (rgb << 8) + color.getBlue();

					jsonEmbed.put("color", rgb);
				}

				final EmbedObject.Footer footer = embed.getFooter();
				final EmbedObject.Image image = embed.getImage();
				final EmbedObject.Thumbnail thumbnail = embed.getThumbnail();
				final EmbedObject.Author author = embed.getAuthor();
				final List<EmbedObject.Field> fields = embed.getFields();

				if (footer != null) {
					final JSONObject jsonFooter = new JSONObject();

					jsonFooter.put("text", footer.getText());
					jsonFooter.put("icon_url", footer.getIconUrl());
					jsonEmbed.put("footer", jsonFooter);
				}

				if (image != null) {
					final JSONObject jsonImage = new JSONObject();

					jsonImage.put("url", image.getUrl());
					jsonEmbed.put("image", jsonImage);
				}

				if (thumbnail != null) {
					final JSONObject jsonThumbnail = new JSONObject();

					jsonThumbnail.put("url", thumbnail.getUrl());
					jsonEmbed.put("thumbnail", jsonThumbnail);
				}

				if (author != null) {
					final JSONObject jsonAuthor = new JSONObject();

					jsonAuthor.put("name", author.getName());
					jsonAuthor.put("url", author.getUrl());
					jsonAuthor.put("icon_url", author.getIconUrl());
					jsonEmbed.put("author", jsonAuthor);
				}

				final List<JSONObject> jsonFields = new ArrayList<JSONObject>();
				for (final EmbedObject.Field field : fields) {
					final JSONObject jsonField = new JSONObject();

					jsonField.put("name", field.getName());
					jsonField.put("value", field.getValue());
					jsonField.put("inline", field.isInline());

					jsonFields.add(jsonField);
				}

				jsonEmbed.put("fields", jsonFields.toArray());
				embedObjects.add(jsonEmbed);

				if (embed.getTimestamp() != null) {
					embed.setTimestampNow();
					jsonEmbed.put("timestamp", embed.getTimestamp());
				}

			}

			json.put("embeds", embedObjects.toArray());
		}

		final URL url = new URL(this.url);
		final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.addRequestProperty("Content-Type", "application/json");
		connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");

		final OutputStream stream = connection.getOutputStream();
		stream.write(json.toString().getBytes());
		stream.flush();
		stream.close();

		connection.getInputStream().close(); // I'm not sure why but it doesn't work without getting the InputStream
		connection.disconnect();
	}

	public static class EmbedObject {
		private String title;
		private String description;
		private String url;
		private Color color;

		private Footer footer;
		private Thumbnail thumbnail;
		private Image image;
		private Author author;
		private final List<Field> fields = new ArrayList<Field>();
		private String timestamp;

		public String getTitle() {
			return this.title;
		}

		public String getDescription() {
			return this.description;
		}

		public String getUrl() {
			return this.url;
		}

		public Color getColor() {
			return this.color;
		}

		public Footer getFooter() {
			return this.footer;
		}

		public Thumbnail getThumbnail() {
			return this.thumbnail;
		}

		public Image getImage() {
			return this.image;
		}

		public Author getAuthor() {
			return this.author;
		}

		public List<Field> getFields() {
			return this.fields;
		}

		public String getTimestamp() {
			return this.timestamp;
		}

		public EmbedObject setTitle(String title) {
			this.title = title;
			return this;
		}

		public EmbedObject setDescription(String description) {
			this.description = description;
			return this;
		}

		public EmbedObject setUrl(String url) {
			this.url = url;
			return this;
		}

		public EmbedObject setColor(Color color) {
			this.color = color;
			return this;
		}

		public EmbedObject setFooter(String text, String icon) {
			this.footer = new Footer(text, icon);
			return this;
		}

		public EmbedObject setThumbnail(String url) {
			this.thumbnail = new Thumbnail(url);
			return this;
		}

		public EmbedObject setImage(String url) {
			this.image = new Image(url);
			return this;
		}

		public EmbedObject setAuthor(String name, String url, String icon) {
			this.author = new Author(name, url, icon);
			return this;
		}

		public EmbedObject addField(String name, String value, boolean inline) {
			this.fields.add(new Field(name, value, inline));
			return this;
		}

		public EmbedObject setTimestampNow() {
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			dateFormat.setTimeZone(TimeZone.getTimeZone(ConfigUtil.getString("webhook.embed.timezone")));
			this.timestamp = dateFormat.format(new Date());
			return this;
		}

		private static class Footer {
			private final String text;
			private final String iconUrl;

			private Footer(String text, String iconUrl) {
				this.text = text;
				this.iconUrl = iconUrl;
			}

			private String getText() {
				return this.text;
			}

			private String getIconUrl() {
				return this.iconUrl;
			}
		}

		private static class Thumbnail {
			private final String url;

			private Thumbnail(String url) {
				this.url = url;
			}

			private String getUrl() {
				return this.url;
			}
		}

		private static class Image {
			private final String url;

			private Image(String url) {
				this.url = url;
			}

			private String getUrl() {
				return this.url;
			}
		}

		private static class Author {
			private final String name;
			private final String url;
			private final String iconUrl;

			private Author(String name, String url, String iconUrl) {
				this.name = name;
				this.url = url;
				this.iconUrl = iconUrl;
			}

			private String getName() {
				return this.name;
			}

			private String getUrl() {
				return this.url;
			}

			private String getIconUrl() {
				return this.iconUrl;
			}
		}

		private static class Field {
			private final String name;
			private final String value;
			private final boolean inline;

			private Field(String name, String value, boolean inline) {
				this.name = name;
				this.value = value;
				this.inline = inline;
			}

			private String getName() {
				return this.name;
			}

			private String getValue() {
				return this.value;
			}

			private boolean isInline() {
				return this.inline;
			}
		}
	}

	private static class JSONObject {

		private final HashMap<String, Object> map = new HashMap<String, Object>();

		void put(String key, Object value) {
			if (value != null) {
				this.map.put(key, value);
			}
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			final Set<Map.Entry<String, Object>> entrySet = this.map.entrySet();
			builder.append("{");

			int i = 0;
			for (final Map.Entry<String, Object> entry : entrySet) {
				final Object val = entry.getValue();
				builder.append(this.quote(entry.getKey())).append(":");

				if (val instanceof String) {
					builder.append(this.quote(String.valueOf(val)));
				} else if (val instanceof Integer) {
					builder.append(Integer.valueOf(String.valueOf(val)));
				} else if (val instanceof Boolean) {
					builder.append(val);
				} else if (val instanceof JSONObject) {
					builder.append(val.toString());
				} else if (val.getClass().isArray()) {
					builder.append("[");
					final int len = Array.getLength(val);
					for (int j = 0; j < len; j++) {
						builder.append(Array.get(val, j).toString()).append(j != (len - 1) ? "," : "");
					}
					builder.append("]");
				}

				i++;
				builder.append(i == entrySet.size() ? "}" : ",");
			}

			return builder.toString();
		}

		private String quote(String string) {
			return "\"" + string + "\"";
		}
	}

}