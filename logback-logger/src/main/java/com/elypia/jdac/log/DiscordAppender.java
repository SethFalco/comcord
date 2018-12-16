package com.elypia.jdac.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

/**
 * A logger for Discord which will redirect some of the logs
 * to a Discord channel.
 */
public final class DiscordAppender extends AppenderBase<ILoggingEvent> {

    private static final String SERVICE = "https://discordapp.com/api/";
    private static final String VERSION = "v6/";

    private static final String SEND_MESSAGE = "channels/:id/messages";
    private static final String OPEN_DM = "users/@me/channels";

    private static final String BOT = "Bot";
    private static final String USER = "User";

    private static final String AUTHORIZATION = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT = "content";

    private static final String POST = "POST";

    /**
     * The name of the application to prepend to logs.
     */
    private String name;

    /**
     * The token used for authenticating to Discord.
     */
    private String token;

    /**
     * This should be either the channel ID to log to
     * if {@link #guild} is set to true, or the user ID to
     * log to if {@link #guild} is set to false. {@link #channel}
     */
    private long id;

    /**
     * Is the type of account that will perform the logging
     * a bot account?<br>
     * <br>
     * <table>
     *     <tr>
     *         <th>Value</th>
     *         <th>Definition</th>
     *     </tr>
     *     <tr>
     *         <td>true</td>
     *         <td>Logging with a bot account.</td>
     *     </tr>
     *     <tr>
     *         <td>false</td>
     *         <td>Logging with a user account.</td>
     *     </tr>
     * </table>
     */
    private boolean bot;

    /**
     * Are you logging to a textchannel in a guild
     * or to a user's DMs?<br>
     * <br>
     * <table>
     *     <tr>
     *         <th>Value</th>
     *         <th>Definition</th>
     *     </tr>
     *     <tr>
     *         <td>true</td>
     *         <td>Log to a guild textchannel.</td>
     *     </tr>
     *     <tr>
     *         <td>false</td>
     *         <td>Log to a user's DMs.</td>
     *     </tr>
     * </table>
     */
    private boolean guild;

    /**
     * <strong>Primarily for internal use.</strong><br>
     * The channel we know we want to log in.
     * If {@link #guild} is true this will be set to whatever
     * {@link #id} is, else we will attempt to {@link #openDMChannel()}
     * to obtain the user's DM channel ID.
     */
    private long channel;

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (channel == 0)
            channel = (guild ? id : openDMChannel());

        if (channel == -1)
            return;

        String message = name + ": " + eventObject.getFormattedMessage();
        sendMessage(message);
    }

    private long openDMChannel() {
        try {
            URL url = new URL(SERVICE + VERSION + OPEN_DM);
            HttpsURLConnection connection = initRequest(url);

            if (connection == null)
                return -1;

            JSONObject formdata = new JSONObject();
            formdata.put("recipient_id", id);

            try (DataOutputStream stream = new DataOutputStream(connection.getOutputStream())) {
                stream.writeBytes(formdata.toString());
            }

            JSONObject response = getResponse(connection);

            if (response == null)
                return -1;

            return response.optLong("id");
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void sendMessage(String message) {
        String service = SERVICE + VERSION + SEND_MESSAGE.replace(":id", String.valueOf(channel));

        try {
            URL url = new URL(service);
            HttpsURLConnection connection = initRequest(url);

            if (connection == null)
                return;

            JSONObject formdata = new JSONObject();
            formdata.put(CONTENT, message);

            try (DataOutputStream stream = new DataOutputStream(connection.getOutputStream())) {
                stream.writeBytes(formdata.toString());
            }

            getResponse(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpsURLConnection initRequest(URL url) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod(POST);

            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            connection.setRequestProperty(AUTHORIZATION, (bot ? BOT : USER) + " " + token);

            connection.setDoOutput(true);

            return connection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject getResponse(HttpsURLConnection connection) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String input;

            while ((input = reader.readLine()) != null)
                response.append(input);

            return new JSONObject(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public boolean isGuild() {
        return guild;
    }

    public void setGuild(boolean guild) {
        this.guild = guild;
    }
}
