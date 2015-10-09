/*
 * Copyright (c) 2013 Rene Puchinger.
 * http://renepuchinger.com
 */

package commshare

class AboutController {

    def index() {
        [ messages: Message.list(max: 5)]
    }

}
