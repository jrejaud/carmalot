package com.jrejaud.carlot.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jrejaud.carlot.R;
import com.jrejaud.carlot.model.Car;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by jrejaud on 10/22/17.
 */

public class CarAdapter extends RealmBaseAdapter<Car> implements ListAdapter {

    private Context context;

    public CarAdapter(Context context, @Nullable OrderedRealmCollection<Car> data) {
        super(data);
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //TODO Recycle views
        view = LayoutInflater.from(context).inflate(R.layout.car_list_item, viewGroup, false);

        ImageView makeImageView = view.findViewById(R.id.make_image);
        TextView modelTextView = view.findViewById(R.id.model_name);
        TextView makeTextView = view.findViewById(R.id.make_name);
        TextView yearTextView = view.findViewById(R.id.model_year);

        if (adapterData!=null) {
            Car car = adapterData.get(i);
            makeTextView.setText(car.getMake());
            modelTextView.setText(car.getModel());
            yearTextView.setText(String.valueOf(car.getYear()));

            //TODO Can use reflection for this too
            switch (car.getMake()) {
                case Car.FORD:
                    makeImageView.setImageResource(R.drawable.ic_ford);
                    break;
                case Car.LEXUS:
                    makeImageView.setImageResource(R.drawable.ic_lexus);
                    break;
                case Car.TESLA:
                    makeImageView.setImageResource(R.drawable.ic_tesla);
                    break;
                case Car.TOYOTA:
                    makeImageView.setImageResource(R.drawable.ic_toyota);
                    break;
                case Car.VW:
                    makeImageView.setImageResource(R.drawable.ic_vw);
                    break;

            }
        }

        return view;
    }


}
