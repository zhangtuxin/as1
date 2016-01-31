package mwteck.com.fillingoilhistory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import bean.FuelUse;
import utils.FileUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FuelUse> mFuelUses = new ArrayList<>();
    private BroadcastReceiver receiver;
    private MyAdapter myAdapter;
    private double allFuelCosts = 0;
    private RelativeLayout relativeLayoutEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayoutEmpty = (RelativeLayout) findViewById(R.id.relativeLayoutEmpty);
        Vector<String> fileNames = FileUtils.getTxetFileName(MainActivity.this);
        for (String fileName : fileNames) {

            String read = FileUtils.read(MainActivity.this, fileName);
            Gson gson = new Gson();
            FuelUse fuelUse = gson.fromJson(read.trim(), FuelUse.class);
            fuelUse.setFileName(fileName);
            mFuelUses.add(fuelUse);
        }
        myAdapter = new MyAdapter();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                recyclerView.setVisibility(View.VISIBLE);
                relativeLayoutEmpty.setVisibility(View.GONE);
                mFuelUses.clear();
                Vector<String> fileNames = FileUtils.getTxetFileName(MainActivity.this);
                for (String fileName : fileNames) {
                    String read = FileUtils.read(MainActivity.this, fileName);
                    Gson gson = new Gson();
                    FuelUse fuelUse = gson.fromJson(read.trim(), FuelUse.class);
                    fuelUse.setFileName(fileName);
                    mFuelUses.add(fuelUse);
                }
                myAdapter.notifyDataSetChanged();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("update_ui");
        registerReceiver(receiver, intentFilter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if (mFuelUses.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            relativeLayoutEmpty.setVisibility(View.VISIBLE);
        }

        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent in = new Intent(MainActivity.this, FuelInfosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("fuel", mFuelUses.get(position));
                in.putExtras(bundle);
                startActivity(in);
            }
        });
        recyclerView.setAdapter(myAdapter);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, EditFuelActivity.class);
                startActivity(in);
            }
        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;
        TextView textViewStationName;
        TextView textViewGrade;
        TextView textViewDollar;
        TextView textViewOdometer;
        TextView textViewAllDollor;
        LinearLayout linearLayoutAllDollar;

        public MyViewHolder(View view) {
            super(view);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            textViewStationName = (TextView) view.findViewById(R.id.textViewStationName);
            textViewGrade = (TextView) view.findViewById(R.id.textViewGrade);
            textViewDollar = (TextView) view.findViewById(R.id.textViewDollar);
            textViewAllDollor = (TextView) view.findViewById(R.id.textViewAllDollor);
            textViewOdometer = (TextView) view.findViewById(R.id.textViewOdometer);
            linearLayoutAllDollar = (LinearLayout) view.findViewById(R.id.linearLayoutAllDollar);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MainActivity.MyViewHolder> {

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @Override
        public MainActivity.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.item_main_fuel, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MainActivity.MyViewHolder holder, final int position) {

            if (position == 0) {
                //show all fuel dollar
                holder.linearLayoutAllDollar.setVisibility(View.VISIBLE);
                allFuelCosts = 0;
                for (FuelUse fue : mFuelUses) {
                    String fuelCost = fue.getFuelCost();
                    allFuelCosts += Double.parseDouble(fuelCost);
                    Log.e("allFuelCosts", String.valueOf(allFuelCosts));
                }
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
                holder.textViewAllDollor.setText(String.valueOf(df.format(allFuelCosts)));
            } else {
                holder.linearLayoutAllDollar.setVisibility(View.GONE);
            }
            holder.textViewDate.setText(mFuelUses.get(position).getDate());
            holder.textViewStationName.setText(mFuelUses.get(position).getStationName());
            holder.textViewGrade.setText(mFuelUses.get(position).getFuelGrade());
            holder.textViewDollar.setText(String.valueOf(mFuelUses.get(position).getFuelCost()));
            holder.textViewOdometer.setText(String.valueOf(mFuelUses.get(position).getOdometer()));
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mFuelUses.size();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
