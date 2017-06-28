package com.kodexx.csorm.users;

import com.kodexx.csorm.backend.data.User;
import com.kodexx.csorm.backend.service.UserService;
import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

public class UsersDataProvider extends AbstractDataProvider<User, String> {

    /** Text filter that can be changed separately. */
    private String filterText = "";


    /**
     * Store given user to the backing data service.
     *
     * @param user
     */

     public void save(User user){
         boolean newUser = user.getId() == -1;

       UserService.get().updateUser(user);
         if(newUser){
             refreshAll();
         }else{
             refreshItem(user);
         }
     }
    
    /**
     * Delete given user from the backing data service.
     *
     * @param user
     *            the user to be deleted
     */
    public void delete(User user) {
        UserService.get().deleteUser(user.getId());
        refreshAll();
    }
    
    /**
     * Sets the filter to use for the this data provider and refreshes data.
     * <p>
     * Filter is compared for username(registration no) or names
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
    
    public Integer getId(User user){
        Objects.requireNonNull(user, "Cannot provide id for a null user");

        return user.getId();
    }
    
    
    
    @Override
    public boolean isInMemory() {
        return true;
    }

    @Override
    public int size(Query<User, String> query) {
       return (int) fetch(query).count();
    }

    @Override
    public Stream<User> fetch(Query<User, String> query) {
       if(filterText.isEmpty()){
           return UserService.get().getAllUsers().stream();
       }
       return UserService.get().getAllUsers().stream().filter(
               file -> passessFilter(file.getUsername(), filterText)
                    || passessFilter(file.getFirstName(), filterText)
                    || passessFilter(file.getLastName(), filterText));
    }
    
    private boolean passessFilter(Object object, String filterText){
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
