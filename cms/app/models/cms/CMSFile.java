package models.cms;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.GenericModel;
import play.modules.search.Field;
import play.modules.search.Indexed;

import java.util.Date;
import java.util.List;

@Entity
@Indexed
@Table(name = "cmsimage")
public class CMSFile extends GenericModel {

    @Id
    @Required
    @Field(sortable = true)
    public String name;

	@Required
    @Field(sortable = true)
	public Boolean isFolder = Boolean.TRUE;

    @Required
    @Field(sortable = true)
    public String title;

    @Field
	public Blob data;

    public Date created = new Date();

    public Date updated = new Date();

    /**
     * Return the list of all item inside the folder.
     *
     * @param path
     * @return
     */
    public static List<CMSFile> getFolderChildren(String path){
        return CMSFile.find("name NOT LIKE '" + path + "%/%' and name like '" + path + "%'").fetch();
    }

    /**
     * Delete a folder, and all under files & folders.
     *
     * @param path
     */
    public static void deleteFolder(String path){
        List<CMSFile> files = CMSFile.find("name like '" + path + "%'").fetch();
        for (CMSFile file: files){
            if (file.data != null && file.data.exists()) {
                file.data.getFile().delete();
            }
            file.delete();
        }
    }

}
