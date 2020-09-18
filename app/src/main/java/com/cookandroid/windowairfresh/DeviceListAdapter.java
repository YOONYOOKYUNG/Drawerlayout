package com.cookandroid.windowairfresh;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Device list adapter.
 *
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *
 */
public class DeviceListAdapter extends BaseAdapter{
	private LayoutInflater mInflater;	//레이아웃 객체화
	private List<BluetoothDevice> mData;
	private OnPairButtonClickListener mListener;

	public DeviceListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public void setData(List<BluetoothDevice> data) {
		mData = data;
	}

	public void setListener(OnPairButtonClickListener listener) {
		mListener = listener;
	}

	public int getCount() {
		return (mData == null) ? 0 : mData.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		//홀더안의 여러가지 뷰를 관리

		if (convertView == null) {
			//뷰가 하나도 없으면
			convertView			=  mInflater.inflate(R.layout.list_item_device, null);
			//리스트 아이템 객체 생성
			holder 				= new ViewHolder();

			holder.nameTv		= (TextView) convertView.findViewById(R.id.tv_name);
			//블루투스 이름
			holder.addressTv 	= (TextView) convertView.findViewById(R.id.tv_address);
			//블루투스 주소
			holder.pairBtn		= (Button) convertView.findViewById(R.id.btn_pair);
			//블루투스 연결 상태

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		BluetoothDevice device	= mData.get(position);

		//블루투스 정보를 가저오는 코드
		holder.nameTv.setText(device.getName());
		holder.addressTv.setText(device.getAddress());
		holder.pairBtn.setText((device.getBondState() == BluetoothDevice.BOND_BONDED) ? "연결해제" : "연결");
		holder.pairBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onPairButtonClick(position);
				}
			}
		});

		return convertView;
	}

	static class ViewHolder {
		TextView nameTv;
		TextView addressTv;
		TextView pairBtn;
	}

	public interface OnPairButtonClickListener {
		void onPairButtonClick(int position);
	}
}