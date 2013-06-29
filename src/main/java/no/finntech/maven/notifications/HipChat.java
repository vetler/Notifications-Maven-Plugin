/** © Copyright 2013 FINN AS
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package no.finntech.maven.notifications;

import com.github.hipchat.api.UserId;
import com.github.hipchat.api.messages.Message;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


/**
 * Goal which sends notifications to hipchat.
 */
@Mojo(name = "hipchat", threadSafe = true)
public final class HipChat extends AbstractMojo {

    /** comma-separated list of rooms to send notifications to. */
    @Parameter(property = "hipchat.rooms", required = false)
    private String hipchatRooms;

    /** user name the message is sent from. */
    @Parameter(property = "hipchat.from", required = false)
    private String hipchatFrom;

    /**
     * hipchat message to send.
     */
    @Parameter(property = "hipchat.message", required = true)
    private String hipchatMessage;

    /**
     * The HipChat API token
     * See https://www.hipchat.com/docs/api
     */
    @Parameter(property = "hipchat.token", required = true)
    private String hipchatToken;

    @Override
    public void execute() throws MojoExecutionException {

        if(null == hipchatToken || hipchatToken.isEmpty()) {
            throw new MojoExecutionException("\nhipchatToken isn't defined.");
        }
        if(null == hipchatMessage || hipchatMessage.isEmpty()) {
            throw new MojoExecutionException("\nhipchatMessage isn't defined.");
        }

        com.github.hipchat.api.HipChat chat = new com.github.hipchat.api.HipChat(hipchatToken);
        for(String room : hipchatRooms.split(",")) {
            chat.getRoom(room).sendMessage(hipchatMessage, UserId.create(hipchatFrom, hipchatFrom), true, Message.Color.PURPLE);
        }
    }
}
