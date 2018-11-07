package com.example.android.mynewsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.data;
import static com.example.android.mynewsapp.NewsAsyncLoader.setURL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * Use the {@link PoliticsNewsFragment} method to
 * create an instance of this fragment.
 */
public class PoliticsNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    public static final String GUARDIAN_POLITICS_URL = "https://content.guardianapis.com/search?q=section=politics&order-by=newest&\"nigeria%20ORafrica%20\"&page-size=50&api-key=2ed206dd-add9-47f0-858c-ea07bf5ee979";
    private ProgressBar progressBar;
    private TextView emptyStateView;
    private ImageView emptyStateImageView;
    private RecyclerView recyclerView;
    public static String emptyState;
    private static ArrayList<News> latestNewsArray;
    private OnListFragmentInteractionListener mListener;

    // Required empty public constructor
    public PoliticsNewsFragment() {
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("Politics", "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_politics_news, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.politics_progress_bar);
        emptyStateView = (TextView) view.findViewById(R.id.politics_empty_state);
        emptyStateImageView = (ImageView) view.findViewById(R.id.politics_empty_state_image);
        recyclerView = (RecyclerView) view.findViewById(R.id.politics_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        getLoaderManager().initLoader(1, null, this).forceLoad();
        setURL(GUARDIAN_POLITICS_URL);
        Log.v("Politics", "onCreateView2 " + emptyState);
        return view;
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     * {@link OnListFragmentInteractionListener} is set to null
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Called when a fragment is first attached to its activity.
     * {@link #onCreate(Bundle)} will be called after this.
     * {@link OnListFragmentInteractionListener} is initialized here
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        Log.v("Politics", "onCreateLoader");

        return new NewsAsyncLoader(this.getContext());
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        if (data == null) {
            emptyState = "Conection Timed out";
        } else if (data.isEmpty()) {
            emptyState = "No Internet Connection";
        } else if (data != null & data.size() != 0) {
            emptyState = "";
            recyclerView.setAdapter(new PoliticsNewsAdapter(data, mListener, getContext()));
            Log.v("Tech", "onLoadFinished  if " + data.get(6).getWebTitle());
        }
        setLoadStatus();
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        Log.v("Politics", "onLoadReset");
        progressBar.setVisibility(View.VISIBLE);
        setLoadStatus();

    }

    /*
    *This is called to update the screen when a load has finished.
    * if Http connection/ JSON parsing was successful, then the progressBar becomes invisible.
    *
    */
    public void setLoadStatus() {
        if (emptyState == "") {
            emptyStateImageView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            emptyStateView.setText(emptyState);
        } else {
            emptyStateImageView.setImageResource(android.R.drawable.stat_sys_warning);
            emptyStateView.setText(emptyState);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(News item);
    }
}
