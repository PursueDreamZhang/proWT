package com.weiteng.weitengapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.bean.MoneyBean;
import com.weiteng.weitengapp.bean.ShopBean;
import com.weiteng.weitengapp.bean.resp.CommonResp;
import com.weiteng.weitengapp.ui.adapter.MoneyListAdapter;
import com.weiteng.weitengapp.ui.adapter.ShopListAdapter;
import com.weiteng.weitengapp.util.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 2017/1/18.
 */

public class DialogUtils {
    public static void showTransferDialog(final Context context) {
        View dlgView = View.inflate(context, R.layout.dlg_transfer, null);
        final EditText et_username = (EditText) dlgView.findViewById(R.id.dlg_transfer_et_username);
        final EditText et_amount = (EditText) dlgView.findViewById(R.id.dlg_transfer_et_amount);
        AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(context);
        dlgBuilder.setTitle("转账");
        dlgBuilder.setView(dlgView);
        dlgBuilder.setPositiveButton("转账", null);
        dlgBuilder.setNegativeButton("取消", null);
        final AlertDialog dlg = dlgBuilder.create();

        dlg.show();
        dlg.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String amount = et_amount.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(context, "请输入对方卡号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(amount)) {
                    Toast.makeText(context, "请输入转账金额", Toast.LENGTH_SHORT).show();
                    return;
                }

                transfer(context, username, amount, dlg);
            }
        });
    }

    public static void showFixedPreorderDialog(final Context context) {
        final MoneyBean[] money = {null};
        final ShopBean[] shop = {null};
        View dlgView = View.inflate(context, R.layout.dlg_preorder_fixed, null);
        final DatePicker dp_date = (DatePicker) dlgView.findViewById(R.id.dlg_preorder_fixed_dp_date);
        final TextView tv_shopid = (TextView) dlgView.findViewById(R.id.dlg_preorder_fixed_tv_shopid);
        final TextView tv_moneyid = (TextView) dlgView.findViewById(R.id.dlg_preorder_fixed_tv_moneyid);
        final ImageButton ib_select_shopid = (ImageButton) dlgView.findViewById(R.id.dlg_preorder_fixed_ib_select_shopid);
        final ImageButton ib_select_moneyid = (ImageButton) dlgView.findViewById(R.id.dlg_preorder_fixed_ib_select_moneyid);

        ib_select_shopid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectShopDialog(context, new DataSelectInterface() {
                    @Override
                    public void onShopSelected(ShopBean shop_) {
                        shop[0] = shop_;
                        tv_shopid.setText(shop_.Sshopname);
                    }

                    @Override
                    public void onMoneySelected(MoneyBean money) {
                    }
                });
            }
        });
        ib_select_moneyid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectMoneyDialog(context, new DataSelectInterface() {
                    @Override
                    public void onShopSelected(ShopBean shop) {
                    }

                    @Override
                    public void onMoneySelected(MoneyBean money_) {
                        money[0] = money_;
                        tv_moneyid.setText("[id:" + money_.id + "]  " + "￥" + money_.money);
                    }
                });
            }
        });

        Calendar calender = Calendar.getInstance(Locale.CHINA);
        dp_date.init(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH), null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("预约提现");
        builder.setView(dlgView);
        builder.setPositiveButton("提现", null);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date timeSelect = new Date(dp_date.getYear() - 1900, dp_date.getMonth(), dp_date.getDayOfMonth());
                Date timeCurrent = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDay());

                long time = timeSelect.getTime();

                String shopid = tv_shopid.getText().toString();
                String moneyid = tv_moneyid.getText().toString();

                if (TextUtils.isEmpty(shopid)) {
                    Toast.makeText(context, "请选择提现商铺", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(moneyid)) {
                    Toast.makeText(context, "请选择提现资金", Toast.LENGTH_SHORT).show();
                    return;
                }

                LogUtils.i("time", "select_time:" + time + "\n\tcurrent_time:" + timeCurrent.getTime());

                if (time < timeCurrent.getTime()) {
                    Toast.makeText(context, "无效预约时间", Toast.LENGTH_SHORT).show();
                } else {
                    final SweetAlertDialog commitDlg = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                    commitDlg.setCancelable(false);
                    commitDlg.setTitleText("正在预约").show();
                    ApiManager.getApiManager().preorder(time, money[0].id, null, shop[0].Susername, new ApiManager.RequestCallBack<CommonResp>() {
                        @Override
                        public void onSuccess(CommonResp data) {
                            switch (data.status) {
                                case 200:
                                    commitDlg.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    commitDlg.setTitleText("预约成功");
                                    alertDialog.dismiss();
                                    break;
                                default:
                                    commitDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    commitDlg.setTitleText("预约失败");
                                    break;
                            }
                        }

                        @Override
                        public void onSuccess(List<CommonResp> data) {
                        }

                        @Override
                        public void onFailure(String error) {
                            commitDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            commitDlg.setTitleText("预约失败");
                        }
                    });
                }
            }
        });
    }

    public static void showUnfixedPreorderDialog(final Context context, String balance) {
        final ShopBean[] shop = {null};
        View dlgView = View.inflate(context, R.layout.dlg_preorder_unfixed, null);
        final DatePicker dp_date = (DatePicker) dlgView.findViewById(R.id.dlg_preorder_unfixed_dp_date);
        final TextView tv_shopid = (TextView) dlgView.findViewById(R.id.dlg_preorder_unfixed_tv_shopid);
        final EditText et_money_amount = (EditText) dlgView.findViewById(R.id.dlg_preorder_unfixed_et_money_amount);
        final ImageButton ib_select_shopid = (ImageButton) dlgView.findViewById(R.id.dlg_preorder_unfixed_ib_select_shopid);
        et_money_amount.setHint("余额：" + balance);

        ib_select_shopid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectShopDialog(context, new DataSelectInterface() {
                    @Override
                    public void onShopSelected(ShopBean shop_) {
                        shop[0] = shop_;
                        tv_shopid.setText(shop_.Sshopname);
                    }

                    @Override
                    public void onMoneySelected(MoneyBean money_) {
                    }
                });
            }
        });

        Calendar calender = Calendar.getInstance(Locale.CHINA);
        dp_date.init(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH), null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("预约提现");
        builder.setView(dlgView);
        builder.setPositiveButton("提现", null);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date timeSelect = new Date(dp_date.getYear() - 1900, dp_date.getMonth(), dp_date.getDayOfMonth());
                Date timeCurrent = new Date(new Date().getYear(), new Date().getMonth(), new Date().getDay());
                long time = timeSelect.getTime();

                String shopid = tv_shopid.getText().toString();
                String money_amount = et_money_amount.getText().toString();

                if (TextUtils.isEmpty(shopid)) {
                    Toast.makeText(context, "请选择提现商铺", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(money_amount)) {
                    Toast.makeText(context, "请选择提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }

                LogUtils.i("time", "select_time:" + time + "\n\tcurrent_time:" + timeCurrent.getTime());

                if (time < timeCurrent.getTime()) {
                    Toast.makeText(context, "无效预约时间", Toast.LENGTH_SHORT).show();
                } else {
                    final SweetAlertDialog commitDlg = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                    commitDlg.setCancelable(false);
                    commitDlg.setTitleText("正在预约").show();
                    ApiManager.getApiManager().preorder(time, "balance", money_amount, shop[0].Susername, new ApiManager.RequestCallBack<CommonResp>() {
                        @Override
                        public void onSuccess(CommonResp data) {
                            switch (data.status) {
                                case 200:
                                    commitDlg.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    commitDlg.setTitleText("预约成功");
                                    alertDialog.dismiss();
                                    break;
                                default:
                                    commitDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    commitDlg.setTitleText("预约失败");
                                    break;
                            }
                        }

                        @Override
                        public void onSuccess(List<CommonResp> data) {
                        }

                        @Override
                        public void onFailure(String error) {
                            commitDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            commitDlg.setTitleText("预约失败");
                        }
                    });
                }
            }
        });
    }

    public static void showSelectShopDialog(final Context context, final DataSelectInterface interf) {
        final List<ShopBean> shops = new ArrayList<>();
        final ShopListAdapter shopListAdapter = new ShopListAdapter(context, shops);
        final SweetAlertDialog progDlg = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progDlg.setCancelable(false);
        progDlg.setTitleText("正在获取数据").show();

        ApiManager.getApiManager().getShopList("微腾", 1, 10, new ApiManager.RequestCallBack<ShopBean>() {
            @Override
            public void onSuccess(ShopBean data) {
            }

            @Override
            public void onSuccess(List<ShopBean> data) {
                shops.addAll(data);
                shopListAdapter.notifyDataSetChanged();
                progDlg.dismiss();

                View dlgView = View.inflate(context, R.layout.dlg_preorder_shoplist, null);
                final EditText tv_keyword = (EditText) dlgView.findViewById(R.id.dlg_preorder_shoplist_et_keyword);
                Button bt_search = (Button) dlgView.findViewById(R.id.dlg_preorder_shoplist_bt_search);
                PullToRefreshListView ptrlv_shoplist = (PullToRefreshListView) dlgView.findViewById(R.id.dlg_preorder_shoplist_ptrlv);

                tv_keyword.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        shops.clear();
                        shopListAdapter.notifyDataSetChanged();
                    }
                });

                bt_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ApiManager.getApiManager().getShopList(tv_keyword.getText().toString(), 1, 10, new ApiManager.RequestCallBack<ShopBean>() {
                            @Override
                            public void onSuccess(ShopBean data) {
                            }

                            @Override
                            public void onSuccess(List<ShopBean> data) {
                                shops.addAll(data);
                                shopListAdapter.notifyDataSetChanged();
                                if (shops.size() == 0) {
                                    Toast.makeText(context, "未搜索到相关店铺", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(String error) {
                                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                ptrlv_shoplist.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                ptrlv_shoplist.setAdapter(shopListAdapter);
                ptrlv_shoplist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                    @Override
                    public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
                        ApiManager.getApiManager().getShopList(tv_keyword.getText().toString(), shops.size() / 10 + 2, 10, new ApiManager.RequestCallBack<ShopBean>() {
                            @Override
                            public void onSuccess(ShopBean data) {
                            }

                            @Override
                            public void onSuccess(List<ShopBean> data) {
                                shops.addAll(data);
                                shopListAdapter.notifyDataSetChanged();
                                refreshView.onRefreshComplete();
                            }

                            @Override
                            public void onFailure(String error) {
                                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
                                refreshView.onRefreshComplete();
                            }
                        });
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("选择商铺");
                builder.setView(dlgView);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                ptrlv_shoplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        interf.onShopSelected(shops.get(i - 1));
                        alertDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                progDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                progDlg.setTitleText("网络错误");
            }
        });
    }

    public static void showSelectMoneyDialog(final Context context, final DataSelectInterface interf) {
        final SweetAlertDialog progDlg = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progDlg.setCancelable(false);
        progDlg.setTitleText("正在获取数据").show();

        ApiManager.getApiManager().getMoney(new ApiManager.RequestCallBack<MoneyBean>() {
            @Override
            public void onSuccess(MoneyBean data) {
            }

            @Override
            public void onSuccess(final List<MoneyBean> data) {
                progDlg.dismiss();
                View dlgView = View.inflate(context, R.layout.dlg_preorder_moneylist, null);
                ListView lv_moneylist = (ListView) dlgView.findViewById(R.id.dlg_preorder_moneylist_lv);
                lv_moneylist.setAdapter(new MoneyListAdapter(context, data));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("选择资金");
                builder.setView(dlgView);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                lv_moneylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        interf.onMoneySelected(data.get(i));
                        alertDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                progDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                progDlg.setTitleText("网络错误");
            }
        });
    }

    private static void transfer(final Context context, final String username, final String amount, final AlertDialog dlg) {
        final SweetAlertDialog progDlg = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        progDlg.setCancelable(false);
        progDlg.setTitleText("正在转账").show();
        ApiManager.getApiManager().transfer(username, amount, new ApiManager.RequestCallBack<CommonResp>() {
            @Override
            public void onSuccess(CommonResp data) {
                switch (data.status) {
                    case 200:
                        dlg.dismiss();
                        progDlg.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        progDlg.setTitleText("转账成功");
                        progDlg.setConfirmText("确定");
                        break;
                    case 403:
                        progDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        progDlg.setTitleText("转账失败");
                        progDlg.setContentText(data.errorinfo);
                        break;
                    case 404:
                        progDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        progDlg.setTitleText("转账失败");
                        progDlg.setContentText(data.errorinfo);
                        break;
                }
            }

            @Override
            public void onSuccess(List<CommonResp> data) {
            }

            @Override
            public void onFailure(String error) {
                progDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                progDlg.setTitleText("网络错误");

                progDlg.setConfirmText("重试").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        transfer(context, username, amount, dlg);
                    }
                });
                progDlg.setCancelText("取消").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
            }
        });
    }

    private interface DataSelectInterface {
        void onShopSelected(ShopBean shop);

        void onMoneySelected(MoneyBean money);
    }

}
