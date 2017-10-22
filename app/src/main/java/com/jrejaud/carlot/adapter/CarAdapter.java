package com.jrejaud.carlot.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jrejaud.carlot.R;
import com.jrejaud.carlot.model.Car;
import com.jrejaud.carlot.model.CarMake;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmQuery;

/**
 * Created by jrejaud on 10/22/17.
 */

public class CarAdapter extends RealmBaseAdapter<Car> implements ListAdapter, Filterable {

    private Context context;
    private Realm realm;

    public CarAdapter(Context context, Realm realm, @Nullable OrderedRealmCollection<Car> data) {
        super(data);
        this.context = context;
        this.realm = realm;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //TODO Recycle views to make things smoother
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
                case CarMake.FORD:
                    makeImageView.setImageResource(R.drawable.ic_ford);
                    break;
                case CarMake.LEXUS:
                    makeImageView.setImageResource(R.drawable.ic_lexus);
                    break;
                case CarMake.TESLA:
                    makeImageView.setImageResource(R.drawable.ic_tesla);
                    break;
                case CarMake.TOYOTA:
                    makeImageView.setImageResource(R.drawable.ic_toyota);
                    break;
                case CarMake.VW:
                    makeImageView.setImageResource(R.drawable.ic_vw);
                    break;

            }
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //This is done by the publishResults method below since this app uses Realm
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                String filter = constraint.toString().toLowerCase().trim();
                if (filter.isEmpty()) {
                    updateData(realm.where(Car.class).findAll());
                    return;
                }

                //Filter by make and model
                RealmQuery<Car> query = realm.where(Car.class)
                        .contains("make", filter, Case.INSENSITIVE)
                        .or()
                        .contains("model", filter, Case.INSENSITIVE);

                //Add a year filter if possible
                try {
                    int yearFilter = -1;
                    yearFilter = Integer.valueOf(filter);
                    query = query.or().equalTo("year", yearFilter);
                } catch (NumberFormatException exception) {
                    //Can't filter by year, don't worry about it
                }

                updateData(query.findAll());
            }
        };
    }
}
