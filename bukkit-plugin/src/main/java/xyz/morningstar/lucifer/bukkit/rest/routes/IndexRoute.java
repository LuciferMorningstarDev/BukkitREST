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
package xyz.morningstar.lucifer.bukkit.rest.routes;

import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;
import xyz.morningstar.lucifer.bukkit.rest.struct.payload.DataPayload;
import xyz.morningstar.lucifer.bukkit.rest.struct.payload.PayloadImpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest.routes:IndexRoute
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @license MIT <https://opensource.org/licenses/MIT>
 * @since 16.02.2022
 */
public class IndexRoute extends HttpHandler {
    private String htmlContent = "";

    public IndexRoute() {
        InputStream in = getClass().getClassLoader().getResourceAsStream("index.min.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            for (String line; (line = reader.readLine()) != null; this.htmlContent += line) ;
        } catch (Exception ex) {
        }
    }

    @Override
    public void service(Request request, Response response) {
        try {
            if (request.getMethod() == (Method.POST) || request.getMethod() == (Method.PUT)
                    || request.getMethod() == (Method.DELETE) || request.getMethod() == (Method.PATCH)
                    || request.getMethod() == (Method.TRACE) || request.getMethod() == (Method.OPTIONS)) {
                DataPayload.create(new Object() {
                    String name = "Bukkit REST Api";
                    String version = "${project.version}";
                    String repository = "https://github.com/LuciferMorningstarDev/BukkitREST";
                    String issues = "https://github.com/LuciferMorningstarDev/BukkitREST/issues";
                    String author = "LuciferMorningstarDev";
                    String authorUrl = "https://github.com/LuciferMorningstarDev/";
                }).write(response);
                return;
            }
            new PayloadImpl(HttpStatus.OK_200, this.htmlContent, false).writeHTML(response);
        } catch (Exception ex) {
        }
    }
}
