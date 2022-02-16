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

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.morningstar.lucifer.bukkit.rest.routes.*;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest:BukkitRestApiPlugin
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public class BukkitRestApiPlugin extends JavaPlugin {

    private static BukkitRestApiPlugin pluginInstance;

    private BukkitRestApiProviderImpl restApiProvider;

    private ServerThread serverThread;

    @Override
    public void onLoad() {
        BukkitRestApiPlugin.pluginInstance = this;
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();

        this.restApiProvider = new BukkitRestApiProviderImpl(this);
        // register API Service things
        BukkitRestApi.setProvider(this.restApiProvider);
        Bukkit.getServicesManager().register(BukkitRestApiProvider.class, this.restApiProvider, this, ServicePriority.High);

        if(!this.getConfig().getBoolean("debug", false)) {
            // async disable grizzly and glassfish debug logging
            new Thread(() -> {
                this.getLogger().info("§6Disabled HttpServer debug Logging...");
                Enumeration<String> loggers = LogManager.getLogManager().getLoggerNames();
                while (loggers.hasMoreElements()) {
                    String loggerName = loggers.nextElement();
                    if (loggerName.toLowerCase().contains("glassfish")) {
                        Logger logger = LogManager.getLogManager().getLogger(loggerName);
                        logger.setLevel(Level.OFF);
                    } else if (loggerName.toLowerCase().contains("grizzly")) {
                        Logger logger = LogManager.getLogManager().getLogger(loggerName);
                        logger.setLevel(Level.OFF);
                    }
                }
            }, "Kill Grizzly Debug Logger").start();
        }

        Thread initializer = new Thread(() -> {
            boolean debug = pluginInstance.getConfig().getBoolean("debug", false);
            pluginInstance.serverThread = new ServerThread(pluginInstance, pluginInstance.restApiProvider, "BukkitREST-WebServer");

            pluginInstance.serverThread.getServer().getHttpServer().getServerConfiguration().setDefaultErrorPageGenerator(new ErrorPageGenerator(pluginInstance));
            pluginInstance.serverThread.getServer().getHttpServer().getServerConfiguration().addHttpHandler(new IndexRoute(), "/");
            if(debug) this.getLogger().info("§bRoute §aEnabled §6'§c/§6'");
            if(pluginInstance.getConfig().getBoolean("enabledRoutes.ping", true)) {
                pluginInstance.serverThread.getServer().addRouteExecutor(new PingRoute(pluginInstance));
                if(debug) this.getLogger().info("§bRoute §aEnabled §6'§c/ping§6'");
            }


            try {
                pluginInstance.serverThread.start();
            } catch(Exception exception) {
                exception.printStackTrace();
                pluginInstance.getLogger().warning("Could not run Server Thread... Disabling Plugin...");
                pluginInstance.getPluginLoader().disablePlugin(pluginInstance);
            }
        }, "BukkitREST-Initialization");
        initializer.start();
        this.getLogger().info("§aEnabled BukkitRestApiPlugin successful...");
    }

    @Override
    public void onDisable() {
        try {
            this.serverThread.getServer().stop();
            this.getLogger().info("§aAPI WebServer Shutdown successful...");
            if(this.serverThread.isAlive()) this.serverThread.interrupt();
            this.serverThread = null;
        } catch(Exception exception) {
            exception.printStackTrace();
            this.getLogger().warning("§cAPI WebServer Shutdown failed...");
        }
        this.getLogger().info("§aDisabled BukkitRestApiPlugin successful...");
    }

    public BukkitRestApiProvider getRestApiProvider() {
        return restApiProvider;
    }

    public ServerThread getServerThread() {
        return serverThread;
    }

}
