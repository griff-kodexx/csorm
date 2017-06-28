package com.kodexx.csorm.files;

import com.kodexx.csorm.backend.data.File;
import com.kodexx.csorm.backend.service.DataService;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

public class FilesDataProvider
        extends AbstractDataProvider<File, String> {

     /** Text filter that can be changed separately. */
    private String filterText = "";


    /**
     * Store given file to the backing data service.
     *
     * @param file
     */

     public void save(File file){
         boolean newFile = file.getId() == -1;

         DataService.get().updateFile(file);
         if(newFile){
             refreshAll();
         }else{
             refreshItem(file);
         }
     }

     /**
     * Delete given file from the backing data service.
     *
     * @param file
     *            the file to be deleted
     */
    public void delete(File file) {
        DataService.get().deleteFile(file.getId());
        refreshAll();
    }

    /**
     * Sets the filter to use for the this data provider and refreshes data.
     * <p>
     * Filter is compared for file code, title or description
     *
     * @param filterText
     *           the text to filter by, never null
     */
    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim();

        refreshAll();
    }

    @Override
    public Integer getId(File file){
        Objects.requireNonNull(file, "Cannot provide id for a null file");

        return file.getId();
    }

    @Override
    public boolean isInMemory() {
        return true;
    }

    @Override
    public int size(Query<File, String> query) {
        return (int) fetch(query).count();
    }

    @Override
    public Stream<File> fetch(Query<File, String> query) {
       if(filterText.isEmpty()){
           return DataService.get().getAllFiles().stream();
       }
       return DataService.get().getAllFiles().stream().filter(
               file -> passessFilter(file.getCode(), filterText)
                    || passessFilter(file.getTitle(), filterText)
                    || passessFilter(file.getDescription(), filterText));
    }

    private boolean passessFilter(Object object, String filterText){
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
