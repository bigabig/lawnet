package webapp.converter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tim on 27.02.2017.
 */
public class ShortcutList {

    private List<String> shortcuts = new ArrayList<String>();

    public ShortcutList()
    {
        String line = "";

        try {
            InputStream is = ShortcutList.class.getResourceAsStream("/shortcuts.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {

                shortcuts.add(line);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<String> getShortcuts() {
        return shortcuts;
    }

}
