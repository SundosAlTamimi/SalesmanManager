package com.dr7.salesmanmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dr7.salesmanmanager.Modles.serialModel;
import com.dr7.salesmanmanager.Reports.SerialReport;

import java.util.ArrayList;
import java.util.List;

import static com.dr7.salesmanmanager.Reports.SerialReport.allseriallist;

public class SerialReportAdpter extends   RecyclerView.Adapter<SerialReportAdpter.SerialReportViewHolder >{
    private List<serialModel> list;
    private List<String> vocherlist=new ArrayList<>();;
    private List<serialModel> serialrowlist=new ArrayList<>();
    Context context;
    View linearLayout;

    public SerialReportAdpter(List<serialModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SerialReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.serialrow, parent, false);
        return new SerialReportAdpter.SerialReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerialReportViewHolder holder, int position) {
        holder.voucherdate.setText(list.get(position).getDateVoucher());
        holder.vouchertype.setText(String.valueOf(list.get(position).getKindVoucher()));
        holder.serialcode.setText(String.valueOf(list.get(position).getSerialCode()));
        holder.itemnum.setText(String.valueOf(list.get(position).getItemNo()));
        holder.vouchernum.setText(String.valueOf(list.get(position).getVoucherNo()));
        holder.export.setVisibility(View.INVISIBLE);
        if(!vocherlist.contains(list.get(position).getVoucherNo())) {
            holder.export.setVisibility(View.VISIBLE);
            vocherlist.add(list.get(position).getVoucherNo());
        }
        holder.export.setTag(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SerialReportViewHolder extends RecyclerView.ViewHolder {
        TextView voucherdate, vouchertype, serialcode, itemnum, vouchernum, export;

        public SerialReportViewHolder(@NonNull View itemView) {

            super(itemView);
            voucherdate = itemView.findViewById(R.id.SE_voucherdate);
            vouchertype = itemView.findViewById(R.id.SE_vocherkind);
            serialcode = itemView.findViewById(R.id.SE__serialcode);
            itemnum = itemView.findViewById(R.id.SE__itemnum);
            vouchernum = itemView.findViewById(R.id.SE_vochernum);
            export = itemView.findViewById(R.id.SE_exportexcel);
            linearLayout = itemView.findViewById(R.id.lin);

            export.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exportToEx();
                }
            });

        }

        private void exportToEx() {
            serialrowlist.clear();
            ExportToExcel exportToExcel = new ExportToExcel();
            final String tag = export.getTag().toString();
            serialModel serialModel = new serialModel();
            Log.e("exportToEx", list.get(Integer.parseInt(tag)).getVoucherNo() + "");
            for (int i = 0; i <allseriallist.size(); i++) {

                if (list.get(Integer.parseInt(tag)).getVoucherNo().equals(allseriallist.get(i).getVoucherNo())) {
                    Log.e("exportToEx", list.get(Integer.parseInt(tag)).getVoucherNo() + "");
                    serialrowlist.add(allseriallist.get(i));
                }
            }

            exportToExcel.createExcelFile(context, "Reportserial.xls", 11, serialrowlist);

        }

    }

}

