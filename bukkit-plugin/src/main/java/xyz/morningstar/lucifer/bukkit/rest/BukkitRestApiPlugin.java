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
        Thread initializer = new Thread(() -> {
            pluginInstance.serverThread = new ServerThread(pluginInstance, pluginInstance.restApiProvider, "BukkitREST-WebServer");
            try {
                pluginInstance.serverThread.start();
            } catch(Exception exception) {
                exception.printStackTrace();
                pluginInstance.getLogger().warning("Could not run Server Thread... Disabling Plugin...");
                pluginInstance.getPluginLoader().disablePlugin(pluginInstance);
            }
        }, "BukkitREST-Initialization");
        initializer.start();
        this.getLogger().info("Enabled BukkitRestApiPlugin successful...");
    }

    @Override
    public void onDisable() {
        try {
            this.serverThread.getServer().stop();
            this.getLogger().info("API WebServer Shutdown successful...");
            if(this.serverThread.isAlive()) this.serverThread.interrupt();
            this.serverThread = null;
        } catch(Exception exception) {
            exception.printStackTrace();
            this.getLogger().warning("API WebServer Shutdown failed...");
        }
        this.getLogger().info("Disabled BukkitRestApiPlugin successful...");
    }

    public BukkitRestApiProvider getRestApiProvider() {
        return restApiProvider;
    }

    public ServerThread getServerThread() {
        return serverThread;
    }

}
