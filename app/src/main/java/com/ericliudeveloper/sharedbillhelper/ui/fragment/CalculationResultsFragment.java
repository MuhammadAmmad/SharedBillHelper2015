package com.ericliudeveloper.sharedbillhelper.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericliudeveloper.sharedbillhelper.R;
import com.ericliudeveloper.sharedbillhelper.ui.presenter.CalculationResultPresenter;
import com.ericliudeveloper.sharedbillhelper.util.ResouceUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculationResultsFragment extends BaseFragment implements CalculationResultPresenter.CalculationResultFace {

    CalculationResultPresenter mPresenter = new CalculationResultPresenter(this);

    private TextView tvSum;
    private TextView tvNumBill;
    private TextView tvNumMember;
    private RecyclerView rvPayment;
    RecyclerView.Adapter mAdapter;

    public CalculationResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mPresenter.setContext(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_calculation_results, container, false);
        setupViews(root);

        mPresenter.calculate();
        mPresenter.refreshDisplay();
        return root;
    }


    private void setupViews(View root) {
        tvSum = (TextView) root.findViewById(R.id.tvSum);
        tvNumBill = (TextView) root.findViewById(R.id.tvNumBill);
        tvNumMember = (TextView) root.findViewById(R.id.tvNumMember);
        rvPayment = (RecyclerView) root.findViewById(R.id.list);

        rvPayment.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PaymentListAdapter();
        rvPayment.setAdapter(mAdapter);

        int pixels = ResouceUtils.getAppResources().getDimensionPixelSize(R.dimen.minimum_heigh_of_recyclerview);
        ViewGroup.LayoutParams params = rvPayment.getLayoutParams();
        params.height = pixels;
        rvPayment.setLayoutParams(params);
        rvPayment.requestLayout();
    }

    @Override
    public void showTotalAmount(String amount) {
        tvSum.setText(amount);
    }

    @Override
    public void showNumberOfMembersPaying(String numMembers) {
        tvNumMember.setText(numMembers);
    }

    @Override
    public void showNumberOfBillsPaid(String numBills) {
        tvNumBill.setText(numBills);
    }


    private class PaymentListAdapter extends RecyclerView.Adapter {


        public PaymentListAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mPresenter.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            mPresenter.bindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return mPresenter.getItemCount();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calculation_result, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {


            case R.id.action_send:
                mPresenter.startActionSend();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
