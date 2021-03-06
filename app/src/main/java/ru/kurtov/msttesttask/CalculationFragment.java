package ru.kurtov.msttesttask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationFragment extends Fragment {

	private MyReceiver myReceiver;
	private FloatingActionButton mCalculateResultFAB;
	private TextView mResultTextView;

	private EditText mFirstOperandET;
	private EditText mSecondOperandET;
	private EditText mSecondsET;

	private String mFirstOperandString;
	private String mSecondOperandString;
	private int mSeconds;


	@Override
	public void onStart() {
		myReceiver = new MyReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(DelayService.MY_ACTION);
		getActivity().registerReceiver(myReceiver, intentFilter);

		mFirstOperandString = "0";
		mSecondOperandString = "0";
		mSeconds = 0;

		super.onStart();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_calculation, container, false);

		mResultTextView = (TextView) v.findViewById(R.id.resultTextView);

		mFirstOperandET = (EditText) v.findViewById(R.id.firstOperandEditText);
		mSecondOperandET = (EditText) v.findViewById(R.id.secondOperandEditText);
		mSecondsET = (EditText) v.findViewById(R.id.secondsEditText);

		mCalculateResultFAB = (FloatingActionButton) v.findViewById(R.id.calculateResultFAB);
		mCalculateResultFAB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				View view = getActivity().getCurrentFocus();
				if (view != null) {
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
							Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
////					mFirstOperandET.setFocusable(false);
//					mFirstOperandET.setFocusable(true);
////					mSecondOperandET.setFocusable(false);
//					mSecondOperandET.setFocusable(true);
////					mSecondsET.setFocusable(false);
//					mSecondsET.setFocusable(true);

				}
				Intent intent = new Intent(getActivity(), DelayService.class);

				mFirstOperandString = mFirstOperandET.getText().toString();
				if (mFirstOperandString.matches("")) {
					mFirstOperandString = "0";
				}

				mSecondOperandString = mSecondOperandET.getText().toString();
				if (mSecondOperandString.matches("")) {
					mSecondOperandString = "0";
				}

				String secondsString = mSecondsET.getText().toString();
				if (secondsString.matches("")) {
					mSeconds = 0;
				} else {
					mSeconds = Integer.parseInt(secondsString);
				}

				intent.putExtra("FIRST", mFirstOperandString);
				intent.putExtra("SECOND", mSecondOperandString);
				intent.putExtra("DELAY", mSeconds);

				mFirstOperandET.setText("");
				mSecondOperandET.setText("");
				mSecondsET.setText("");

				mCalculateResultFAB.setClickable(false);
				mCalculateResultFAB.setFocusable(false);
				mCalculateResultFAB.setEnabled(false);


				String textToShow = String.format(getString(R.string.seconds_to_show), mSeconds);
				Toast.makeText(getActivity(), textToShow, Toast.LENGTH_SHORT).show();

				getActivity().startService(intent);
			}
		});

		return v;
	}

	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String theResult = arg1.getStringExtra("DATAPASSED");
			String firstOp = arg1.getStringExtra("FIRST_OP");
			String secondOp = arg1.getStringExtra("SECOND_OP");

			mCalculateResultFAB.setClickable(true);
			mCalculateResultFAB.setFocusable(true);
			mCalculateResultFAB.setEnabled(true);


			String text = String.format("%s + %s = %s", firstOp, secondOp, theResult);
			mResultTextView.setText(text);
		}

	}

	@Override
	public void onStop() {
		getActivity().unregisterReceiver(myReceiver);
		super.onStop();
	}


}
