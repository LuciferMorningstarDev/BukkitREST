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

import org.glassfish.grizzly.http.server.Request;
import xyz.morningstar.lucifer.bukkit.rest.BukkitRestApiPlugin;
import xyz.morningstar.lucifer.bukkit.rest.json.GsonConverter;
import xyz.morningstar.lucifer.bukkit.rest.struct.payload.DataPayload;
import xyz.morningstar.lucifer.bukkit.rest.struct.payload.DataPayloadImpl;
import xyz.morningstar.lucifer.bukkit.rest.struct.payload.Payload;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest.routes:ErrorPageGenerator
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 16.02.2022
 */
public class ErrorPageGenerator implements org.glassfish.grizzly.http.server.ErrorPageGenerator {

    private BukkitRestApiPlugin plugin;

    public ErrorPageGenerator(BukkitRestApiPlugin plugin) {
    }

    @Override
    public String generate(Request request, int errorCode, String s, String s1, Throwable throwable) {
        switch(errorCode) {
            case 404 -> {
                return GsonConverter.toJsonString(Payload.notFound());
            }
            case 500 -> {
                return GsonConverter.toJsonString(new DataPayloadImpl(Payload.internalServerError(s), s1));
            }
            default -> {
                return null;
            }
        }
    }
}
