package cl.aguzman.prueba3.views.main.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.aguzman.prueba3.R;
import cl.aguzman.prueba3.adapters.ContentCallback;
import cl.aguzman.prueba3.adapters.ListTalesAdapter;
import cl.aguzman.prueba3.data.Nodes;
import cl.aguzman.prueba3.data.Preloading;
import cl.aguzman.prueba3.views.ContentActivity;

public class ListAllTalesFragment extends Fragment implements ContentCallback{

    private Preloading preloading = new Preloading();
    private ListTalesAdapter adapter;
    public ListAllTalesFragment() {
    }

    public static ListAllTalesFragment newInstance(){
        return new ListAllTalesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_all_tales, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preloading.preloadingShow(getContext(), getString(R.string.preload_message));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.allListTaleRv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        adapter = new ListTalesAdapter(this, new Nodes().talesRef().orderByChild("publish").equalTo(true));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getContent(String uid, String key) {
        Intent intent = new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    @Override
    public void dataChange(){
        preloading.progressDialog.dismiss();
    }

}
