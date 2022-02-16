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
package xyz.morningstar.lucifer.bukkit.rest;

import org.glassfish.grizzly.http.server.HttpServer;
import xyz.morningstar.lucifer.bukkit.rest.configuration.ServerConfiguration;
import xyz.morningstar.lucifer.bukkit.rest.listener.HTTPListener;
import xyz.morningstar.lucifer.bukkit.rest.listener.HTTPSListener;
import xyz.morningstar.lucifer.bukkit.rest.routing.RouteExecutor;

import java.io.IOException;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest:ApiWebServerImpl
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public class ApiWebServerImpl implements ApiWebServer {

    private final ServerConfiguration serverConfiguration;

    private final HttpServer httpServer;

    private org.glassfish.grizzly.http.server.ServerConfiguration sConf;

    public ApiWebServerImpl(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
        this.httpServer = new HttpServer();
        this.httpServer.removeListener("grizzly"); // remove default grizzly listeners
        this.httpServer.addListener(new HTTPListener(serverConfiguration));
        if(serverConfiguration.isSecure()) {
            this.httpServer.addListener(new HTTPSListener(serverConfiguration));
        }
        this.sConf = this.httpServer.getServerConfiguration();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> httpServer.shutdownNow()));
    }

    @Override
    public void start() {
        try {
            this.httpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            this.httpServer.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HttpServer getHttpServer() {
        return httpServer;
    }

    @Override
    public org.glassfish.grizzly.http.server.ServerConfiguration getConfig() {
        return this.sConf;
    }

    @Override
    public boolean addRouteExecutor(RouteExecutor executor) {
        try {
            getConfig().addHttpHandler(executor, executor.getName());
            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ServerConfiguration getServerConfiguration() {
        return serverConfiguration;
    }

}
