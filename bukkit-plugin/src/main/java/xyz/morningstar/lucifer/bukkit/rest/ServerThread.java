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

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest:ServerThread
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public class ServerThread extends Thread {

    private final BukkitRestApiProvider api;
    private final BukkitRestApiPlugin holder;

    public ServerThread(BukkitRestApiPlugin holder, BukkitRestApiProvider api, String threadName) {
        super(threadName);
        this.holder = holder;
        this.api = api;
    }

    @Override
    public void run() {
        try {
            this.api.getServer().start();
        } catch(Exception exception) {
            exception.printStackTrace();
            this.holder.getLogger().warning("Could not run Server... Disabling Plugin...");
            this.holder.getPluginLoader().disablePlugin(this.holder);
        }
    }

    public ApiWebServer getServer() {
        return this.api.getServer();
    }

    public BukkitRestApiProvider getApi() {
        return this.api;
    }

}
