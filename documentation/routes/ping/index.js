/**
 * @api {get} /ping Server Ping - get a reply when server is available
 * @apiName ServerPing
 * @apiGroup Misc/Tools
 * @apiSuccess {Object} ping object
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     {
 *       "error": false,
 *       "ping": true,
 *     }
 */