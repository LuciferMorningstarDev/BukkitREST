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
package xyz.morningstar.lucifer.bukkit.rest.routing;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import xyz.morningstar.lucifer.bukkit.rest.struct.RequestData;
import xyz.morningstar.lucifer.bukkit.rest.struct.payload.DataPayload;
import xyz.morningstar.lucifer.bukkit.rest.struct.payload.Payload;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest.routing:RouteExecutor
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public abstract class RouteExecutor extends HttpHandler {

    private String path;

    public RouteExecutor(String path) {
        this.path = path;
    }

    protected abstract Payload execute(RequestData requestData);

    @Override
    public void service(Request request, Response response) {
        try {
            Payload payload = execute(RequestData.from(request));
            payload.write(response);
        } catch (Exception exception) {
            try {
                DataPayload.internalServerError(exception).write(response);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String path() {
        return path;
    }

}