package in.menukart.menukart.view.explore;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.menukart.menukart.R;
import in.menukart.menukart.api.ApiClient;
import in.menukart.menukart.entities.explore.Restaurant;
import in.menukart.menukart.entities.explore.RestaurantList;
import in.menukart.menukart.presenter.explore.RestaurantListPresenterImp;

public class ExploreFragment extends Fragment implements RestaurantListView {

    RestaurantListPresenterImp restaurantListPresenterImp;
    RecyclerView recyclerViewExplore;
    SearchView searchViewRestaurant;
    ExploreAdapter exploreAdapter;
  //  SwitchCompat switchCompatVeg;

    View root;
    private String TAG = "MainActivity";
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_explore, container, false);

        initExploreViews();


        return root;
    }

    private void initExploreViews() {
        context = getActivity();
        recyclerViewExplore = root.findViewById(R.id.recycler_view_restaurants);
        searchViewRestaurant = root.findViewById(R.id.search_view_food);
      //  switchCompatVeg = root.findViewById(R.id.switch_veg_only);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewExplore.setLayoutManager(layoutManager);
        restaurantListPresenterImp = new RestaurantListPresenterImp(this,
                new ApiClient(context));


        if (ApiClient.isConnectedToInternet(context)) {
            getListOfRestaurantsData();
        } else {
            ApiClient.openAlertDialogWithPositive(context, getString(R.string.error_check_network),
                    getString(R.string.dialog_label_ok));
        }

        setSearchFilter();
      /*  if (switchCompatVeg.isEnabled())
        {
            setVegOnlyFilter();
        }*/

    }

    private void setVegOnlyFilter()
    {
    }

    private void setSearchFilter() {
        // listening to search query text change
        searchViewRestaurant.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                //  if (restaurantLists.contains(query)) {
                if (exploreAdapter != null) {
                    exploreAdapter.getFilter().filter(query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if (exploreAdapter != null) {
                    exploreAdapter.getFilter().filter(query);
                }

                return false;
            }
        });

    }

    private void getListOfRestaurantsData() {
        ApiClient.showProgressBar(context);
        restaurantListPresenterImp.requestRestaurantList();

    }

    @Override
    public void showError(String error) {
        ApiClient.hideProgressBar();
        Log.d(TAG, "onRegistercitizenError: error");
        Toast.makeText(context, getString(R.string.error_invalid_response), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessfulRestaurantList(RestaurantList restaurantList) {
        ApiClient.hideProgressBar();
        if (restaurantList.getList() != null) {
            exploreAdapter = new ExploreAdapter(context,restaurantList.getList());
            recyclerViewExplore.setAdapter(exploreAdapter);
            exploreAdapter.notifyDataSetChanged();
        }

    }
}