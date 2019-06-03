package cn.yyd.fashiontech.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.yyd.fashiontech.R;


/**
 * Allows an abstraction of the ViewHolder pattern.<br>
 * <br>
 * <p/>
 * <b>Usage</b>
 * <p/>
 * <pre>
 * return AdapterHelper.get(context, convertView, parent, R.layout.item)
 *         .setText(R.id.tvName, contact.getName())
 *         .setText(R.id.tvEmails, contact.getEmails().toString())
 *         .setText(R.id.tvNumbers, contact.getNumbers().toString())
 *         .getView();
 * </pre>
 */
public class AdapterHelper extends BaseAdapterHelper {

    private int position;

    /** Package private field to retain the associated user object and detect a change */
    Object associatedObject;

    protected AdapterHelper(Context context, ViewGroup parent, int layoutId, int position) {
        super(layoutId, parent, context);
        this.position = position;
        convertView.setTag(R.id.view_tag, this);
    }
    protected AdapterHelper(View itemView, int position) {
        super(itemView);
        this.position = position;
        convertView.setTag(R.id.view_tag,this);
    }

    /**
     * This method is the only entry point to get a AdapterHelper.
     * @param context     The current context.
     * @param convertView The convertView arg passed to the getView() method.
     * @param parent      The parent arg passed to the getView() method.
     * @return A AdapterHelper instance.
     */
    public static BaseAdapterHelper get(Context context, View convertView, ViewGroup parent, int layoutId) {
        return get(context, convertView, parent, layoutId, -1);
    }

    /** This method is package private and should only be used by QuickAdapter. */
    static AdapterHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new AdapterHelper(context, parent, layoutId, position);
        }

        // Retrieve the existing helper and update its position
        AdapterHelper existingHelper = (AdapterHelper) convertView.getTag(R.id.view_tag);
        existingHelper.position = position;
        return existingHelper;
    }
    /** This method is package private and should only be used by QuickAdapter. */
    static AdapterHelper get(Context context, View convertView, ViewGroup parent, int position, ViewItemCreator creator) {
        if (convertView == null) {
            return new AdapterHelper(creator.createItemView(position,parent, LayoutInflater.from(context)), position);
        }

        // Retrieve the existing helper and update its position
        AdapterHelper existingHelper = (AdapterHelper) convertView.getTag(R.id.view_tag);
        existingHelper.position = position;
        return existingHelper;
    }

    /**
     * 当使用 代码创建 ItemView 时，实现该接口即可
     */
    interface ViewItemCreator{
        View createItemView(int position, ViewGroup parent, LayoutInflater inflater);
    }

    /**
     * Will download an image from a URL and put it in an ImageView.<br/>
     * It uses Square's Picasso library to download the image asynchronously and put the result into the ImageView.<br/>
     * Picasso manages recycling of views in a ListView.<br/>
     * If you need more control over the Picasso settings, use {AdapterHelper#setImageBuilder}.
     * @param viewId   The view id.
     * @param imageUrl The image URL.
     * @return The AdapterHelper for chaining.
     */
//    public AdapterHelper setImageUrl(int viewId, String imageUrl) {
//        ImageView view = retrieveView(viewId);
//        Picasso.with(context).load(imageUrl).into(view);
//        return this;
//    }
//
//    /**
//     * Will download an image from a URL and put it in an ImageView.<br/>
//     * @param viewId         The view id.
//     * @param requestBuilder The Picasso request builder. (e.g. Picasso.with(context).load(imageUrl))
//     * @return The AdapterHelper for chaining.
//     */
//    public AdapterHelper setImageBuilder(int viewId, RequestCreator requestBuilder) {
//        ImageView view = retrieveView(viewId);
//        requestBuilder.into(view);
//        return this;
//    }

    /** Retrieve the convertView */
    public <T extends View> T  getView() {
        return (T)convertView;
    }

    /**
     * Retrieve the overall position of the data in the list.
     * @throws IllegalArgumentException If the position hasn't been set at the construction of the this helper.
     */
    public int getPosition() {
        if (position == -1)
            throw new IllegalStateException("Use AdapterHelper constructor " +
                    "with position if you need to retrieve the position.");
        return position;
    }

    /** Retrieves the last converted object on this view. */
    public Object getAssociatedObject() {
        return associatedObject;
    }

    /** Should be called during convert */
    public void setAssociatedObject(Object associatedObject) {
        this.associatedObject = associatedObject;
    }
}
