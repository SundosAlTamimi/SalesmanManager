package com.dr7.salesmanmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dr7.salesmanmanager.Modles.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohd darras on 15/04/2018.
 */

public class CustomersListAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Customer> mOriginalValues;
    private List<Customer> custList;
    private CustomerListShow customerListShow;

    public CustomersListAdapter(CustomerListShow customerListShow, Context context, List<Customer> custList) {
        this.context = context;
        this.mOriginalValues = custList;
        this.custList = custList;
        this.customerListShow = customerListShow;

    }

    public void setItemsList(List<Customer> custList) {
        this.custList = custList;
    }

    @Override
    public int getCount() {
        return custList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        LinearLayout linearLayout;
        TextView custAccountTextView;
        TextView custNameTextView;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.customers_item, null);

        holder.linearLayout = (LinearLayout) view.findViewById(R.id.LinearLayout01);
        holder.custAccountTextView = (TextView) view.findViewById(R.id.custAccTextView);
        holder.custNameTextView = (TextView) view.findViewById(R.id.custNameTextView);


        holder.custAccountTextView.setText("" + custList.get(i).getCustId());
        holder.custNameTextView.setText(custList.get(i).getCustName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(custList.get(i).getIsSuspended()!=1)
                {
                CustomerListShow.Customer_Name = custList.get(i).getCustName();
                CustomerListShow.Customer_Account = custList.get(i).getCustId() + "";
                CustomerListShow.CashCredit = custList.get(i).getCashCredit();
                CustomerListShow.PriceListId = custList.get(i).getPriceListId();
                CustomerListShow.paymentTerm = custList.get(i).getPayMethod();
                    if((custList.get(i).getCustLat() == null)||(custList.get(i).getCustLong() == null))
                    {
                        CustomerListShow.latitude="";
                                CustomerListShow.longtude="";
                    }
                    else {
                        CustomerListShow.latitude=custList.get(i).getCustLat();
                        CustomerListShow.longtude=custList.get(i).getCustLong();
                    }



                CustomerListShow.CreditLimit = custList.get(i).getCreditLimit();
                CustomerListShow.Max_Discount_value = custList.get(i).getMax_discount();
                if (custList.get(i).getIsSuspended() == 1)
                {
                    Toast toast = Toast.makeText(context, "This customer is susbended", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 180);
                    ViewGroup group = (ViewGroup) toast.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(25);
                    toast.show();
                } else
                    {
                    CustomerCheckInFragment customerCheckInFragment = new CustomerCheckInFragment();
                    customerCheckInFragment.settext1();

                }

                    CustomerListShow.CustHideValu=custList.get(i).getHide_val();
                    customerListShow.dismiss();


            }
                else{
                    Log.e("cust",""+custList.get(i).getIsSuspended());
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(context.getResources().getString(R.string.cusSuspended));
                    builder.setTitle(context.getResources().getString(R.string.warning_message));
                    builder.setPositiveButton(context.getResources().getString(R.string.app_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }


                    });
                    builder.create().show();
                }
            }
        });

        return view;
    }



    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                custList = (ArrayList<Customer>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
//                Log.e("here" , "*********1" + custList.get(0).getCustName());
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Customer> FilteredArrList = new ArrayList<Customer>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Customer>(custList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getCustName();
                        int paymetho = mOriginalValues.get(i).getPayMethod();

                        Log.e("mOriginalpaymetho" , "paymetho"+paymetho);
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(new Customer(mOriginalValues.get(i).getCustId(),mOriginalValues.get(i).getCustName(),mOriginalValues.get(i).getPayMethod()));
                            Log.e("mOriginalValues" , "*********2" + constraint + "*" + mOriginalValues.get(i).getPayMethod());
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;

//                    Log.e("here" , "*********3" + FilteredArrList.get(0).getCustName());
                }
                return results;
            }
        };
        return filter;
    }
}
