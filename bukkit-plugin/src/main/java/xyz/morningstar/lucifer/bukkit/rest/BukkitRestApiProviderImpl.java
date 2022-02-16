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

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.morningstar.lucifer.bukkit.rest.configuration.DatabaseConfiguration;
import xyz.morningstar.lucifer.bukkit.rest.configuration.ServerConfiguration;
import xyz.morningstar.lucifer.bukkit.rest.struct.HostAndPort;

import java.util.ArrayList;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest:BukkitRestApiProviderImpl
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public class BukkitRestApiProviderImpl implements BukkitRestApiProvider {

    private final ApiWebServerImpl apiWebServer;
    private final ServerConfiguration serverConfiguration;

    public BukkitRestApiProviderImpl(BukkitRestApiPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        ArrayList<String> enabledRoutes = new ArrayList<>();
        ConfigurationSection routesSection = config.getConfigurationSection("enabledRoutes");
        routesSection.getKeys(false).forEach(routeString -> {
            if(routesSection.getBoolean(routeString, false)) enabledRoutes.add(routeString);
        });
        if(config.getBoolean("SSL.enabled", false)) {
            this.serverConfiguration = new ServerConfiguration(
                    config.getBoolean("debug", false),
                    config.getString("hostname", "127.0.0.1"),
                    config.getBoolean("hostCheck", false),
                    config.getString("storage", "YAML"),
                    new DatabaseConfiguration(new HostAndPort(config.getString("database.host", "127.0.0.1"),
                            config.getInt("database.port", 27017)),
                            config.getString("database.name"), config.getString("database.user"),
                            config.getString("database.password"), config.getString("database.authenticationDatabase")),
                    new HostAndPort(config.getString("bind", "127.0.0.1"), config.getInt("port", 80)),
                    new HostAndPort(config.getString("bind", "127.0.0.1"), config.getInt("port", 443)),
                    config.getString("", null),
                    config.getString("", null),
                    enabledRoutes
            );
        } else {
            this.serverConfiguration = new ServerConfiguration(
                    config.getBoolean("debug", false),
                    config.getString("hostname", "127.0.0.1"),
                    config.getBoolean("hostCheck", false),
                    config.getString("storage"),
                    new DatabaseConfiguration(new HostAndPort(config.getString("database.host", "127.0.0.1"),
                            config.getInt("database.port", 27017)),
                            config.getString("database.name"), config.getString("database.user"),
                            config.getString("database.password"), config.getString("database.authenticationDatabase")),
                    new HostAndPort(config.getString("bind", "127.0.0.1"), config.getInt("port", 80)),
                    enabledRoutes
            );
        }
        this.apiWebServer = new ApiWebServerImpl(this.serverConfiguration);
    }

    public ServerConfiguration getServerConfiguration() {
        return serverConfiguration;
    }

    public ApiWebServerImpl getApiWebServer() {
        return apiWebServer;
    }

    @Override
    public ApiWebServer getServer() {
        return apiWebServer;
    }

}
