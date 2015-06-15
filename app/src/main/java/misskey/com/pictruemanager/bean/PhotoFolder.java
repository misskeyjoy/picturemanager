package misskey.com.pictruemanager.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by misskey on 15-6-4.
 */
public class PhotoFolder implements Serializable{
    private List<AlbumInfo> list;

    public List<AlbumInfo> getList() {
        return list;
    }

    public void setList(List<AlbumInfo> list) {
        this.list = list;
    }

}
