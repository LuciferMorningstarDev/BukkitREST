/**
 * BukkitREST | Copyright (c) 2022 LuciferMorningstarDev
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package xyz.morningstar.lucifer.bukkit.rest.struct.payload;

import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;
import xyz.morningstar.lucifer.bukkit.rest.json.GsonConverter;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest.struct.payload:PayloadImpl
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 16.02.2022
 */
public class PayloadImpl implements Payload{

    public boolean error;
    public String message;
    public HttpStatus status;

    public PayloadImpl(HttpStatus status, String message, boolean error) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    @Override
    public boolean error() {
        return error;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public boolean write(Response response) {
        try {
            response.setStatus(this.status());
            response.setContentType("application/json");
            String json = GsonConverter.toJsonString(this);
            response.setContentLength(json.length());
            response.getWriter().write(json);
        } catch(Exception ex) {
            try {
                DataPayload.internalServerError(ex).write(response);
            } catch(Exception e) {}
            return false;
        }
        return true;
    }

    public boolean writeHTML(Response response) {
        try {
            response.setStatus(this.status());
            response.setContentType(message.startsWith("<") ? "text/html" : "text/plain");
            response.setContentLength(message.length());
            response.getWriter().write(message);
        } catch(Exception ex) {
            try {
                DataPayload.internalServerError(ex).write(response);
            } catch(Exception e) {}
            return false;
        }
        return true;
    }

}
