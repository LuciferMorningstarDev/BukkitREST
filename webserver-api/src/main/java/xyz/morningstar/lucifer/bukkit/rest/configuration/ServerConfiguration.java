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
package xyz.morningstar.lucifer.bukkit.rest.configuration;

import xyz.morningstar.lucifer.bukkit.rest.struct.HostAndPort;

import java.util.List;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest.configuration:ServerConfiguration
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public class ServerConfiguration {

    private final boolean debugMode;

    private final String host;
    private final boolean hostChecking;

    private final HostAndPort httpBind;
    private final HostAndPort httpsBind;

    private final boolean secure;

    private final List<String> enabledRoutes;

    private final String certificatePemPath;
    private final String privateKeyPemPath;

    private final String storageMethod;

    public ServerConfiguration(boolean debug, String host, boolean hostChecking, String storageMethod, DatabaseConfiguration databaseConfiguration, HostAndPort httpBind, HostAndPort httpsBind, String certificatePem, String privateKeyPem, List<String>enabledRoutes) {
        this.debugMode = debug;
        this.host = host;
        this.hostChecking = hostChecking;
        this.storageMethod = storageMethod;
        this.httpBind = httpBind;
        this.httpsBind = httpsBind;
        this.certificatePemPath = certificatePem;
        this.privateKeyPemPath = privateKeyPem;
        this.secure = true;
        this.enabledRoutes = enabledRoutes;
    }

    public ServerConfiguration(boolean debug, String host, boolean hostChecking, String storageMethod, DatabaseConfiguration databaseConfiguration, HostAndPort httpBind, List<String> enabledRoutes) {
        this.debugMode = debug;
        this.host = host;
        this.hostChecking = hostChecking;
        this.storageMethod = storageMethod;
        this.httpBind = httpBind;
        this.httpsBind = null;
        this.certificatePemPath = null;
        this.privateKeyPemPath = null;
        this.secure = false;
        this.enabledRoutes = enabledRoutes;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public String getHost() {
        return host;
    }

    public String getStorageMethod() {
        return storageMethod;
    }

    public boolean isHostChecking() {
        return hostChecking;
    }

    public HostAndPort getHttpBind() {
        return httpBind;
    }

    public HostAndPort getHttpsBind() {
        return httpsBind;
    }

    public String getCertificatePemPath() {
        return certificatePemPath;
    }

    public String getPrivateKeyPemPath() {
        return privateKeyPemPath;
    }

    public boolean isSecure() {
        return secure;
    }

    public List<String> getEnabledRoutes() {
        return enabledRoutes;
    }

}
