package com.cookandroid.windowairfresh;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Device list.
 *
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *
 */


public class DeviceListActivity extends AppCompatActivity {

	final int NEW_WINDOW_REQUEST=1234;
	public static int page = 1;
	public static String btaddress = "";
	private BluetoothAdapter mBluetoothAdapter; // 블루투스 어댑터

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if(resultCode==RESULT_OK && requestCode==NEW_WINDOW_REQUEST){
			Log.d("dhkim", "전달받은 새로운 창문 이름 : " + data.getStringExtra("new_window_name"));
			Log.d("dhkim", "전달받은 새로운 창문 주소 : " + data.getStringExtra("btaddress"));
			setResult(RESULT_OK, data);
			DeviceListActivity.this.finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	String[] WindowList = new String[7];
	private ListView mListView, mListView2;
	private DeviceListAdapter mAdapter,mAdapter2;
	private ArrayList<BluetoothDevice> mDeviceList,mDeviceList2;
	private Button btrefreshbutton;
	private ProgressDialog mProgressDlg; //로딩중 화면

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devicelist);
		//어느 페이지의 레이아웃인가요?
		mDeviceList = getIntent().getExtras().getParcelableArrayList("device.list");
			mDeviceList2 = getIntent().getExtras().getParcelableArrayList("device.list2");
			mListView = (ListView) findViewById(R.id.lv_paired);
			btrefreshbutton = (Button) findViewById(R.id.btrefresh);
			mListView2 = (ListView) findViewById(R.id.lv_paired2);
			mAdapter = new DeviceListAdapter(this);
			mAdapter2 = new DeviceListAdapter(this);
			mAdapter.setData(mDeviceList);
			mAdapter2.setData(mDeviceList2);
			mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
				@Override
				public void onPairButtonClick(int position) {
					//버튼을 클릭하면
					BluetoothDevice device = mDeviceList.get(position);
					if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
						//BOND_BONDED : 페어링 됐으면
						unpairDevice(device);
						//페어링 해제
					} else {
						btaddress=device.getAddress();
						Log.d("테스트", "처음으로 저장된 주소 : "+btaddress);
						showToast("연결중...");
						pairDevice(device); //페어링
					}
				}
			});

			mListView.setAdapter(mAdapter);

			mAdapter2.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
				@Override
				public void onPairButtonClick(int position) {
					//버튼을 클릭하면
					BluetoothDevice device = mDeviceList2.get(position);
					if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
						//BOND_BONDED : 페어링 됐으면
						unpairDevice(device);
						//페어링 해제
					} else {
						btaddress=device.getAddress();
						Log.d("테스트", "처음으로 저장된 주소 : "+btaddress);
						showToast("연결중...");
						pairDevice(device); //페어링
					}
				}
			});

			mListView2.setAdapter(mAdapter2);
			registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));

			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		mProgressDlg = new ProgressDialog(this);
		mProgressDlg.setMessage("검색중...");
		mProgressDlg.setCancelable(false);
		//뒤로가기 버튼을 눌러도 false임으로 뒤로가지지 않습니다.
		mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

				mBluetoothAdapter.cancelDiscovery();
				//Cancel 버튼을 누르면 블루투스 찾기가 종료됩니다.
			}
		});

		btrefreshbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBluetoothAdapter.startDiscovery();

				IntentFilter filter = new IntentFilter();
				//IntentFilter란 다음 작업이 명시되지 않은 상태에서 보내진 intent에 대해
				//어느 activity/sevice/broadcast가 받을 것인가를 찾는 Intent Resolution시
				//참조하는 정보이다

				filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
				//디바이스 연결상태 변경
				filter.addAction(BluetoothDevice.ACTION_FOUND);
				//블루투스 디바이스가 검색되었을 때(디바이스 검색 결과)
				filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
				//블루투스 디바이스 검색 시작
				filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
				//블루투스 디바이스 검색이 끝났을 때
				registerReceiver(mReceiver, filter);
				//receiver를 등록한다
			}
		});
	}

	@Override
	public void onPause() {
			if (mBluetoothAdapter.isDiscovering()) {
				//블루투스 기기를 발견했다면
				mBluetoothAdapter.cancelDiscovery();
				//블루투스 검색 취소
				}
		super.onPause();
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(mPairReceiver);
		super.onDestroy();
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			//각 action에 따른 반응
			if  (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				//블루투스 디바이스 검색 시작
				mDeviceList = new ArrayList<BluetoothDevice>();
				//블루투스 기기 목록 갱신
				mProgressDlg.show();
				//로딩중화면 표시
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				//블루투스 디바이스 검색이 끝났을 때
				mProgressDlg.dismiss();
				//로딩중 화면 사라짐
				return;
			} else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				//블루투스 디바이스가 검색되었을 때(디바이스 검색 결과)
				BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				//추가된 값 받아오기
				mDeviceList.add(device);

			}
		}
	};

	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	private void pairDevice(BluetoothDevice device) {
		//선택한 디바이스 페어링 요청
		try {
			Method method = device.getClass().getMethod("createBond", (Class[]) null);
			method.invoke(device, (Object[]) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void unpairDevice(BluetoothDevice device) {
		//선택한 디바이스 언페어링 요청
		try {
			Method method = device.getClass().getMethod("removeBond", (Class[]) null);
			method.invoke(device, (Object[]) null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
				//페어링 상태가 변경되면 페어링상태 업데이트
				final int state 		= intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
				final int prevState	= intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

				if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
					showToast("연결됨");
					page++;
					if(page==2||page==3)
					{
						finish();
					}
					if(page==4){
					Intent windowintent = new Intent(DeviceListActivity.this, WindowNameActivity.class);
						Log.d("테스트", "인텐트 바로 보내기전 주소 : "+btaddress);
						windowintent.putExtra("btaddress",btaddress);
						startActivityForResult(windowintent, NEW_WINDOW_REQUEST);

						page=1;}
				}
				else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
						showToast("연결해제됨");
					}
					//어댑터값 갱신
					mAdapter.notifyDataSetChanged();
					mAdapter2.notifyDataSetChanged();
			}
		}
	};
	@Override
	public void onBackPressed(){
		DeviceListActivity.this.finish();
		super.onBackPressed();
	}
}