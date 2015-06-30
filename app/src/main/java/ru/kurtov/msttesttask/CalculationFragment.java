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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by KURT on 29.06.2015.
 */
public class CalculationFragment extends Fragment {

	private MyReceiver myReceiver;
	private FloatingActionButton mCalculateResultFAB;
	private TextView mResultTextView;

	private EditText mFirstOperandET;
	private EditText mSecondOperandET;
	private EditText mSecondsET;

	@Override
	public void onStart() {

		myReceiver = new MyReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(DelayService.MY_ACTION);
		getActivity().registerReceiver(myReceiver, intentFilter);

		//Start our own service


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
				Intent intent = new Intent(getActivity(), DelayService.class);

				double firstOperand = Double.parseDouble(mFirstOperandET.getText().toString());
				double secondOperand = Double.parseDouble(mSecondOperandET.getText().toString());
				int seconds = Integer.parseInt(mSecondsET.getText().toString());

				intent.putExtra("DELAY", seconds);
				intent.putExtra("FIRST", firstOperand);
				intent.putExtra("SECOND", secondOperand);

				mFirstOperandET.setText("");
				mSecondOperandET.setText("");
				mSecondsET.setText("");

				mCalculateResultFAB.setClickable(false);

				String textToShow = String.format("The result will be shown in %s second", seconds);
				if (seconds > 1) {
					textToShow = textToShow + "s";
				}
				Toast.makeText(getActivity(),textToShow, Toast.LENGTH_SHORT).show();

				getActivity().startService(intent);
			}
		});

		return v;
	}

	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub

			double datapassed = arg1.getDoubleExtra("DATAPASSED", 0);
			double firstOp = arg1.getDoubleExtra("FIRST_OP", 0);
			double secondOp = arg1.getDoubleExtra("SECOND_OP", 0);

			mCalculateResultFAB.setClickable(true);

			String text = String.format("%s + %s = %s",
					new BigDecimal(firstOp).setScale(6, RoundingMode.CEILING).stripTrailingZeros().toPlainString(),
					new BigDecimal(secondOp).setScale(6, RoundingMode.CEILING).stripTrailingZeros().toPlainString(),
					new BigDecimal(datapassed).setScale(6, RoundingMode.CEILING).stripTrailingZeros().toPlainString());
			mResultTextView.setText(text);


		}

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(myReceiver);
		super.onStop();
	}
}
