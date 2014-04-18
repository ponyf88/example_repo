package com.example.client;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Widget;

public class Disqus {

    public static boolean developer = false;
    public static String shortname = "ponyf88";

    public static void showComments(Widget where, String identifier) {
        showComments(where.getElement(), identifier);
    }

    public static void showComments(Element where, String identifier) {
        if (shortname == null)
            throw new IllegalArgumentException(
                      "You must specify the disqus shortname before displaying comments");

        // Store the original id of the target element
        String id = where.getId();
        if (id == null) {
            id = "disqus-" + Integer.toHexString(Random.nextInt());
            where.setId(id);
        }

        // Update the id temporarily
        where.setId("disqus_thread");

        // Load the comments
        loadComments(id, shortname, identifier, developer);
    }

    private static native void loadComments(String container_id, String shortname, String identifier, boolean developer) /*-{
        // CONFIGURATION VARIABLES
        $wnd.disqus_container_id = container_id;
        $wnd.disqus_developer = developer ? 1 : 0;
        $wnd.disqus_shortname = shortname; // required
        if (identifier) $wnd.disqus_identifier = identifier;

        // TODO
        // disqus_url

        // disqus_title

        // disqus_category_id

        // DON'T EDIT BELOW THIS LINE (sorry, I've edited it anyway)
        (function() {
            var dsq = $doc.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
            dsq.src = 'http://' + shortname + '.disqus.com/embed.js';
            ($doc.getElementsByTagName('head')[0] || $doc.getElementsByTagName('body')[0]).appendChild(dsq);
        })();
    }-*/;
}