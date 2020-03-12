package com.example.inserttest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.BaseMarkerBalloon;
import com.tomtom.online.sdk.map.Icon;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.Marker;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.map.RouteBuilder;
import com.tomtom.online.sdk.map.SingleLayoutBalloonViewAdapter;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.routing.data.RouteResponse;
import com.tomtom.online.sdk.routing.data.RouteType;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResponse;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResult;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TomtomMap tomtomMap;
    private SearchApi searchApi;
    private RoutingApi routingApi;
    private Route route;
    private LatLng departurePosition;
    private LatLng destinationPosition;
    private LatLng wayPointPosition;
    private Icon departureIcon;
    private Icon destinationIcon;
    private Button btnHelp;
    private ImageButton btnClear;
    private ImageButton btnGasStation;
    private ImageButton btnRestaurant;
    private ImageButton btnAtm;
    private ImageButton btnSearch;
    private EditText editTextPois;
    private Dialog dialogInProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTomTomServices();
        initUIViews();
        setupUIViewListeners();
        disableSearchButtons();
    }
    @Override
    public void onMapReady(@NonNull final TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
        this.tomtomMap.setMyLocationEnabled(true);
        this.tomtomMap.getMarkerSettings().setMarkersClustering(true);
        this.tomtomMap.getMarkerSettings().setMarkerBalloonViewAdapter(createCustomViewAdapter());

        //SOURCE
        searchApi.reverseGeocoding(new ReverseGeocoderSearchQueryBuilder(19.2029, 72.861557).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ReverseGeocoderSearchResponse>() {
                    @Override
                    public void onSuccess(ReverseGeocoderSearchResponse response) {
                        dismissDialogInProgress();
                        processResponse(response);
                    }
                    @Override
                    public void onError(Throwable e) {
                        handleApiError(e);
                    }
                    private void processResponse(ReverseGeocoderSearchResponse response) {
                        if (response.hasResults()) {
                            processFirstResult(response.getAddresses().get(0).getPosition());
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.geocode_no_results), Toast.LENGTH_SHORT).show();
                        }
                    }
                    private void processFirstResult(LatLng geocodedPosition) {
                        setAndDisplayDeparturePosition(geocodedPosition);
                    }
                    private void setAndDisplayDeparturePosition(LatLng geocodedPosition) {
                        departurePosition = geocodedPosition;
                        createMarkerIfNotPresent(departurePosition, departureIcon);
                    }
                });
        //Destination
        searchApi.reverseGeocoding(new ReverseGeocoderSearchQueryBuilder(18.7557, 73.4091).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ReverseGeocoderSearchResponse>() {
                    @Override
                    public void onSuccess(ReverseGeocoderSearchResponse response) {
                        dismissDialogInProgress();
                        processResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleApiError(e);
                    }

                    private void processResponse(ReverseGeocoderSearchResponse response) {
                        if (response.hasResults()) {
                            processFirstResult(response.getAddresses().get(0).getPosition());
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.geocode_no_results), Toast.LENGTH_SHORT).show();
                        }
                    }

                    private void processFirstResult(LatLng geocodedPosition) {
                        destinationPosition = geocodedPosition;
                        tomtomMap.removeMarkers();
                        drawRoute(departurePosition, destinationPosition);
                        enableSearchButtons();
                    }

                    private void setAndDisplayDeparturePosition(LatLng geocodedPosition) {
                        departurePosition = geocodedPosition;
                        createMarkerIfNotPresent(departurePosition, departureIcon);
                    }
                });
    }
    @Override
    public void onBackPressed() {
        if (!isMapCleared()) {
            clearMap();
        } else {
            super.onBackPressed();
        }
    }
    private boolean isMapCleared() {
        return departurePosition == null
                && destinationPosition == null
                && route == null;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void initTomTomServices() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getAsyncMap(this);
        }
        searchApi = OnlineSearchApi.create(this);
        routingApi = OnlineRoutingApi.create(this);
    }
    private void initUIViews() {
        departureIcon = Icon.Factory.fromResources(MainActivity.this, R.drawable.ic_map_route_departure);
        destinationIcon = Icon.Factory.fromResources(MainActivity.this, R.drawable.ic_map_route_destination);
        editTextPois = findViewById(R.id.edittext_main_poisearch);
        btnAtm = findViewById(R.id.btn_main_atm);
        btnHelp = findViewById(R.id.btn_main_help);
        btnClear = findViewById(R.id.btn_main_clear);
        btnGasStation = findViewById(R.id.btn_main_gasstation);
        btnRestaurant = findViewById(R.id.btn_main_restaurant);
        btnSearch = findViewById(R.id.btn_main_poisearch);
        dialogInProgress = new Dialog(this);
        dialogInProgress.setContentView(R.layout.dialog_in_progress);
        dialogInProgress.setCanceledOnTouchOutside(false);
    }
    private void setupUIViewListeners() {
        View.OnClickListener searchButtonListener = getSearchButtonListener();
        btnSearch.setOnClickListener(searchButtonListener);
        btnGasStation.setOnClickListener(searchButtonListener);
        btnRestaurant.setOnClickListener(searchButtonListener);
        btnAtm.setOnClickListener(searchButtonListener);
        btnHelp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        });
        btnClear.setOnClickListener(v -> clearMap());
        editTextPois.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deselectShortcutButtons();
            }
        });
    }
    @NonNull
    private View.OnClickListener getSearchButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchClick(v);
            }

            private void handleSearchClick(View v) {
                if (isRouteSet()) {
                    Optional<CharSequence> description = Optional.fromNullable(v.getContentDescription());

                    if (description.isPresent()) {
                        editTextPois.setText(description.get());
                        deselectShortcutButtons();
                        v.setSelected(true);
                    }
                    if (isWayPointPositionSet()) {
                        tomtomMap.clear();
                        drawRoute(departurePosition, destinationPosition);
                    }
                    String textToSearch = editTextPois.getText().toString();
                    if (!textToSearch.isEmpty()) {
                        tomtomMap.removeMarkers();
                        searchAlongTheRoute(route, textToSearch);
                    }
                }
            }
            private boolean isRouteSet() {
                return route != null;
            }
            private boolean isWayPointPositionSet() {
                return wayPointPosition != null;
            }
            private void searchAlongTheRoute(Route route, final String textToSearch) {
                final Integer MAX_DETOUR_TIME = 1000;
                final Integer QUERY_LIMIT = 10;
                disableSearchButtons();
                showDialogInProgress();
                searchApi.alongRouteSearch(new AlongRouteSearchQueryBuilder(textToSearch, route.getCoordinates(), MAX_DETOUR_TIME)
                        .withLimit(QUERY_LIMIT)
                        .build())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<AlongRouteSearchResponse>() {
                            @Override
                            public void onSuccess(AlongRouteSearchResponse response) {
                                displaySearchResults(response.getResults());
                                dismissDialogInProgress();
                                enableSearchButtons();
                            }
                            private void displaySearchResults(List<AlongRouteSearchResult> results) {
                                if (!results.isEmpty()) {
                                    for (AlongRouteSearchResult result : results) {
                                        createAndDisplayCustomMarker(result.getPosition(), result);
                                    }
                                    tomtomMap.zoomToAllMarkers();
                                } else {
                                    Toast.makeText(MainActivity.this, String.format(getString(R.string.no_search_results), textToSearch), Toast.LENGTH_LONG).show();
                                }
                            }
                            private void createAndDisplayCustomMarker(LatLng position, AlongRouteSearchResult result) {
                                String address = result.getAddress().getFreeformAddress();
                                String poiName = result.getPoi().getName();

                                BaseMarkerBalloon markerBalloonData = new BaseMarkerBalloon();
                                markerBalloonData.addProperty(getString(R.string.poi_name_key), poiName);
                                markerBalloonData.addProperty(getString(R.string.address_key), address);

                                MarkerBuilder markerBuilder = new MarkerBuilder(position)
                                        .markerBalloon(markerBalloonData)
                                        .shouldCluster(true);
                                tomtomMap.addMarker(markerBuilder);
                            }
                            @Override
                            public void onError(Throwable e) {
                                handleApiError(e);
                                enableSearchButtons();
                            }
                        });
            }
        };
    }

    private void disableSearchButtons() {
        btnSearch.setEnabled(false);
        btnGasStation.setEnabled(false);
        btnAtm.setEnabled(false);
        btnRestaurant.setEnabled(false);
        btnClear.setEnabled(false);
    }
    private void enableSearchButtons() {
        btnSearch.setEnabled(true);
        btnGasStation.setEnabled(true);
        btnAtm.setEnabled(true);
        btnRestaurant.setEnabled(true);
        btnClear.setEnabled(true);
    }
    private void deselectShortcutButtons() {
        btnGasStation.setSelected(false);
        btnRestaurant.setSelected(false);
        btnAtm.setSelected(false);
    }
    private void showDialogInProgress() {
        if (!dialogInProgress.isShowing()) {
            dialogInProgress.show();
        }
    }
    private void dismissDialogInProgress() {
        if (dialogInProgress.isShowing()) {
            dialogInProgress.dismiss();
        }
    }
    private SingleLayoutBalloonViewAdapter createCustomViewAdapter() {
        return new SingleLayoutBalloonViewAdapter(R.layout.marker_custom_balloon) {
            @Override
            public void onBindView(View view, final Marker marker, BaseMarkerBalloon baseMarkerBalloon) {
                Button btnAddWayPoint = view.findViewById(R.id.btn_balloon_waypoint);
                TextView textViewPoiName = view.findViewById(R.id.textview_balloon_poiname);
                TextView textViewPoiAddress = view.findViewById(R.id.textview_balloon_poiaddress);
                textViewPoiName.setText(baseMarkerBalloon.getStringProperty(getApplicationContext().getString(R.string.poi_name_key)));
                textViewPoiAddress.setText(baseMarkerBalloon.getStringProperty(getApplicationContext().getString(R.string.address_key)));
                btnAddWayPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setWayPoint(marker);
                    }

                    private void setWayPoint(Marker marker) {
                        wayPointPosition = marker.getPosition();
                        tomtomMap.clearRoute();
                        drawRouteWithWayPoints(departurePosition, destinationPosition, new LatLng[]{wayPointPosition});
                        marker.deselect();
                    }
                });
            }
        };
    }
    private void createMarkerIfNotPresent(LatLng position, Icon icon) {
        Optional<Marker> optionalMarker = tomtomMap.findMarkerByPosition(position);
        if (!optionalMarker.isPresent()) {
            tomtomMap.addMarker(new MarkerBuilder(position)
                    .icon(icon));
        }
    }
    private void clearMap() {
        tomtomMap.clear();
        departurePosition = null;
        destinationPosition = null;
        route = null;
        disableSearchButtons();
        editTextPois.getText().clear();
    }
    private void handleApiError(Throwable e) {
        dismissDialogInProgress();
        Toast.makeText(MainActivity.this, getString(R.string.api_response_error, e.getLocalizedMessage()), Toast.LENGTH_LONG).show();
    }
    private RouteQuery createRouteQuery(LatLng start, LatLng stop, LatLng[] wayPoints) {
        return (wayPoints != null) ?
                new RouteQueryBuilder(start, stop).withWayPoints(wayPoints).withRouteType(RouteType.FASTEST).build() :
                new RouteQueryBuilder(start, stop).withRouteType(RouteType.FASTEST).build();
    }
    private void drawRoute(LatLng start, LatLng stop) {
        wayPointPosition = null;
        drawRouteWithWayPoints(start, stop, null);
    }
    private void drawRouteWithWayPoints(LatLng start, LatLng stop, LatLng[] wayPoints) {
        RouteQuery routeQuery = createRouteQuery(start, stop, wayPoints);
        showDialogInProgress();
        routingApi.planRoute(routeQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<RouteResponse>() {

                    @Override
                    public void onSuccess(RouteResponse routeResult) {
                        dismissDialogInProgress();
                        displayRoutes(routeResult.getRoutes());
                        tomtomMap.displayRoutesOverview();
                    }

                    private void displayRoutes(List<FullRoute> routes) {
                        for (FullRoute fullRoute : routes) {
                            route = tomtomMap.addRoute(new RouteBuilder(
                                    fullRoute.getCoordinates()).startIcon(departureIcon).endIcon(destinationIcon));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleApiError(e);
                        clearMap();
                    }
                });
    }

    private boolean isDestinationPositionSet() {
        return destinationPosition != null;
    }

    private boolean isDeparturePositionSet() {
        return departurePosition != null;
    }

    private abstract class BaseTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
