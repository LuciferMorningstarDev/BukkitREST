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
package xyz.morningstar.lucifer.bukkit.rest.struct;

import com.google.gson.JsonObject;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.Request;
import xyz.morningstar.lucifer.bukkit.rest.json.GsonConverter;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest.struct:RequestData
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public interface RequestData {

    default Method[] allowed() {
        return new Method[] { Method.GET, Method.POST, Method.PUT, Method.PATCH };
    }

    Request original();

    String methodString();

    Method method();

    String action();

    String body();

    JsonObject payloadData();
    
    class DefaultImpl implements RequestData {

        private Request request;
        private String body;

        public DefaultImpl(Request request) {
            this.request = request;
            try {
                Buffer postBody = request.getPostBody(4096);
                this.body = postBody.toStringContent();
            } catch (Exception exception) {
                this.body = null;
                exception.printStackTrace();
            }
        }

        @Override
        public Request original() {
            return request;
        }

        @Override
        public String methodString() {
            return request.getMethod().toString();
        }

        @Override
        public Method method() {
            return request.getMethod();
        }

        @Override
        public String action() {
            try { return payloadData().get("action").getAsString(); } catch(Exception e) { return null; }
        }

        @Override
        public String body() {
            return body;
        }

        @Override
        public JsonObject payloadData() {
            try { return GsonConverter.toJson(body); } catch(Exception e) { return null; }
        }

    }

    static RequestData from(Request request) {
        return new DefaultImpl(request);
    }

}