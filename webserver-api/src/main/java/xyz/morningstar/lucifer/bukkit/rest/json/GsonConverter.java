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
package xyz.morningstar.lucifer.bukkit.rest.json;

import com.google.gson.*;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest.json:GsonConverter
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public class GsonConverter {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJsonString(Object someInstanceOfStaff) {
        try {
            return gson.toJson(someInstanceOfStaff);
        } catch(Exception e) {
            return null;
        }
    }

    public static <T> T toType(String someJsonStuff, Class type) {
        try {
            return (T) gson.fromJson(someJsonStuff,type);
        } catch(Exception e) {
            return null;
        }
    }

    public static JsonObject toJson(String someJsonStuff) {
        try {
            return JsonParser.parseString(someJsonStuff).getAsJsonObject();
        } catch(Exception e) {
            return null;
        }
    }

    public static String toJsonString(JsonElement jsonElement) {
        try {
            return gson.toJson(jsonElement);
        } catch(Exception e) {
            return null;
        }
    }

}
