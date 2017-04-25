package cn.yyd.nestedscroll;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {
    private static final String TAG = "ScrollingActivity";

    private RecyclerView mMenuGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        final TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);

        final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
                final int scrollRange = layout.getTotalScrollRange();
                //计算标题透明度
                float titleAlpha = (1F - ((scrollRange + verticalOffset) / (float) scrollRange));
                tvTitle.setAlpha(titleAlpha);

                Log.i(TAG, "onOffsetChanged: recyclerHeight " + mMenuGrid.getHeight());
            }
        });

        appBarLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override public boolean onPreDraw() {
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
                if (behavior != null) {
                    behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                        @Override public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
//                            final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0
//                                    && child.hasScrollableChildren()
//                                    && parent.getHeight() - directTargetChild.getHeight() <= child.getHeight();
                            //如果 CoordinatorLayout 的高度 ，减去 NestedScrollView 的高度。比 AppBarLayout 的高度小，
                            //我们就让 AppBarLayout 可以拖动。否则，空间足够，就不允许 AppbarLayout 滑动
                            ViewGroup parent = (ViewGroup) appBarLayout.getParent();
                            return parent.getHeight() - mMenuGrid.getHeight() <= appBarLayout.getHeight();
                        }
                    });
                    appBarLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                Log.i(TAG, "onWindowAttached: behavior==" + behavior);
                return true;
            }
        });


        mMenuGrid = (RecyclerView) findViewById(R.id.recycler_menu_grid);
        mMenuGrid.setLayoutManager(new GridLayoutManager(this, 2));

        mMenuGrid.setAdapter(new MenuAdapter(getMenus()));
    }

    private List<MenuItem> getMenus() {
        List<MenuItem> menus = new ArrayList<>();

        menus.add(new MenuItem(R.string.txt_menu_achievement, R.drawable.ic_menu_achievement));
        menus.add(new MenuItem(R.string.txt_menu_dish_list, R.drawable.ic_menu_dishlist));
        menus.add(new MenuItem(R.string.txt_menu_member, R.drawable.ic_menu_member));
        menus.add(new MenuItem(R.string.txt_menu_order_manager, R.drawable.ic_menu_order_manager));
        menus.add(new MenuItem(R.string.txt_menu_ordering, R.drawable.ic_menu_ordering));


        return menus;
    }

    class MenuHolder extends RecyclerView.ViewHolder {

        TextView item;

        public MenuHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.tv_menu_item);
        }
    }

    class MenuAdapter extends RecyclerView.Adapter<MenuHolder> {
        private List<MenuItem> list;

        MenuAdapter(List<MenuItem> list) {
            this.list = list;
        }

        @Override public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MenuHolder(getLayoutInflater().inflate(R.layout.item_menu_item, parent, false));
        }

        @Override public void onBindViewHolder(MenuHolder holder, int position) {
            MenuItem menu = list.get(position);
            holder.item.setText(menu.menuName);
            holder.item.setCompoundDrawablesWithIntrinsicBounds(0, menu.drawable, 0, 0);
        }


        @Override public int getItemCount() {
            return list.size();
        }
    }

    class MenuItem {
        @StringRes int menuName;
        @DrawableRes int drawable;
        public MenuItem(int menuName, int drawable) {
            this.menuName = menuName;
            this.drawable = drawable;
        }
    }
}
