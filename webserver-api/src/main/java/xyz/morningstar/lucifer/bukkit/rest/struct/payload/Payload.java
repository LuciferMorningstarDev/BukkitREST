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
package xyz.morningstar.lucifer.bukkit.rest.struct.payload;

import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;
import xyz.morningstar.lucifer.bukkit.rest.json.GsonConverter;
import xyz.morningstar.lucifer.bukkit.rest.struct.Struct;

import java.io.IOException;

/**
 * BukkitREST; xyz.morningstar.lucifer.bukkit.rest.struct.payload:Payload
 *
 * @license MIT <https://opensource.org/licenses/MIT>
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 15.02.2022
 */
public interface Payload extends Struct {

    boolean error();

    String message();

    HttpStatus status();

    boolean write(Response response) throws IOException;

    static Payload ok() {
        return ok("OK_200");
    }

    static Payload ok(String message) {
        return new PayloadImpl(HttpStatus.OK_200, message, false);
    }

    static Payload accepted() {
        return accepted("ACCEPTED_202");
    }

    static Payload accepted(String message) {
        return new PayloadImpl(HttpStatus.ACCEPTED_202, message, false);
    }

    static Payload badRequest() {
        return badRequest("BAD_REQUEST_400");
    }

    static Payload badRequest(String message) {
        return new PayloadImpl(HttpStatus.BAD_REQUEST_400, message, true);
    }

    static Payload unauthorized() {
        return unauthorized("UNAUTHORIZED_401");
    }

    static Payload unauthorized(String message) {
        return new PayloadImpl(HttpStatus.UNAUTHORIZED_401, message, true);
    }

    static Payload forbidden() {
        return forbidden("FORBIDDEN_403");
    }

    static Payload forbidden(String message) {
        return new PayloadImpl(HttpStatus.FORBIDDEN_403, message, true);
    }

    static Payload notFound() {
        return notFound("NOT_FOUND_404");
    }

    static Payload notFound(String message) {
        return new PayloadImpl(HttpStatus.NOT_FOUND_404, message, true);
    }

    static Payload notImplemented() {
        return notImplemented("NOT_IMPLEMENTED_501");
    }

    static Payload notImplemented(String message) {
        return new PayloadImpl(HttpStatus.NOT_IMPLEMENTED_501, message, true);
    }

    static Payload methodNotAllowed() {
        return methodNotAllowed("METHOD_NOT_ALLOWED_405");
    }

    static Payload methodNotAllowed(String message) {
        return new PayloadImpl(HttpStatus.METHOD_NOT_ALLOWED_405, message, true);
    }

    static Payload internalServerError() {
        return internalServerError("INTERNAL_SERVER_ERROR_500");
    }

    static Payload internalServerError(String message) {
        return new PayloadImpl(HttpStatus.INTERNAL_SERVER_ERROR_500, message, true);
    }

    static Payload badGateway() {
        return badGateway("BAD_GATEWAY_502");
    }

    static Payload badGateway(String message) {
        return new PayloadImpl(HttpStatus.BAD_GATEWAY_502, message, true);
    }

    static void catchException(Response response, Exception e) {
        try {
            DataPayload.internalServerError(e).write(response);
        } catch(Exception ex) {}
    }

}