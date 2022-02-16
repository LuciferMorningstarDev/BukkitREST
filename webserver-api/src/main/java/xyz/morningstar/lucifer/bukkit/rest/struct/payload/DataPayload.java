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

import java.io.IOException;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest.struct.payload:DataPayload
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public interface DataPayload extends Payload {

    Payload payload();

    Object data();

    class DefaultImpl implements DataPayload {

        private final Payload payload;
        private final Object data;

        public DefaultImpl() {
            this.payload = Payload.ok();
            this.data = null;
        }

        public DefaultImpl(Object data) {
            this.payload = Payload.ok();
            this.data = data;
        }

        public DefaultImpl(Payload payload, Object data) {
            this.payload = payload;
            this.data = data;
        }

        @Override
        public Object data() {
            return data;
        }

        @Override
        public Payload payload() {
            return payload;
        }

        @Override
        public boolean error() {
            return payload().error();
        }

        @Override
        public String message() {
            return payload().message();
        }

        @Override
        public HttpStatus status() {
            return payload().status();
        }

        @Override
        public boolean write(Response response) throws IOException {
            response.setStatus(status());
            response.setContentType("application/json");
            String json = GsonConverter.toJsonString(this);
            response.setContentLength(json.length());
            response.getWriter().write(json);
            return true;
        }
    }

    static DataPayload create(Object data) {
        return new DefaultImpl(data);
    }

    static DataPayload create(Payload payload, Object data) {
        return new DefaultImpl(payload, data);
    }

    static DataPayload internalServerError(Exception exception) {
        return new DefaultImpl(internalServerError(exception.getMessage()), exception.getStackTrace());
    }

    static Payload internalServerError(String message) {
        return Payload.internalServerError(message);
    }

}