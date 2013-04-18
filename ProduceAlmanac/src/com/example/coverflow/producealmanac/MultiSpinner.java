package com.example.coverflow.producealmanac;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class MultiSpinner extends Spinner implements OnMultiChoiceClickListener, OnCancelListener {

    private List<String> items;
    private boolean[] selected;
    private String defaultText;
    private MultiSpinnerListener listener;

    public MultiSpinner(Context context) {
    	super(context);
	}
	
	public MultiSpinner(Context arg0, AttributeSet arg1) {
	    super(arg0, arg1);
	}
	
	public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
	    super(arg0, arg1, arg2);
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
	    if (isChecked)
	        selected[which] = true;
	    else
	        selected[which] = false;
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
	    // refresh text on spinner
	    StringBuffer spinnerBuffer = new StringBuffer();
	    boolean someUnselected = false;
	    for (int i = 0; i < items.size(); i++) {
	        if (selected[i] == true) {
	            spinnerBuffer.append(items.get(i));
	            spinnerBuffer.append(", ");
	        } else {
	            someUnselected = true;
	        }
	    }
	    String spinnerText;
	    if (someUnselected) {
	        spinnerText = spinnerBuffer.toString();
	        if (spinnerText.length() > 2)
	            spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
	    } else {
	        spinnerText = defaultText;
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
	            android.R.layout.simple_spinner_item,
	            new String[] { spinnerText });
	    setAdapter(adapter);
	    listener.onItemsSelected(selected);
	}
	
	@Override
	public boolean performClick() {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
	    System.out.println("size of items: " + items.size());
	    builder.setMultiChoiceItems(
	            items.toArray(new CharSequence[items.size()]), selected, this);
	    builder.setPositiveButton(com.example.coverflow.R.string.all,
	            new DialogInterface.OnClickListener() {
	
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                	// do nothing here.
	                }
	            });
	    builder.setNeutralButton(com.example.coverflow.R.string.none,
	            new DialogInterface.OnClickListener() {
	
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                	// do nothing here.
	                }
	            });
	    builder.setNegativeButton(com.example.coverflow.R.string.done,
	            new DialogInterface.OnClickListener() {
	
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    dialog.cancel();
	                }
	            });
	    builder.setOnCancelListener(this);
	    final AlertDialog dialog = builder.create();
	    //builder.show();
	    dialog.show();
	    
	    // set up all, none buttons not to close the dialog,
	    // and set what's happen when it is clicked.
	    Button button_all = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
	    button_all.setOnClickListener(new View.OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		System.out.println("All Clicked");
                ListView list = ((AlertDialog) dialog).getListView();
                for (int i = 0; i < list.getCount(); i++) {
                	list.setItemChecked(i, true);
                	selected[i] = true;
                }
	    	}
	    });
	    
	    Button button_none = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
	    button_none.setOnClickListener(new View.OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		System.out.println("None Clicked");
                ListView list = ((AlertDialog) dialog).getListView();
                for (int i = 0; i < list.getCount(); i++) {
                	list.setItemChecked(i, false);
                	selected[i] = false;
                }
	    	}
	    });

	    return true;
	}
	
	public void setItems(List<String> items, String allText,
	    MultiSpinnerListener listener) {
	    this.items = items;
	    this.defaultText = allText;
	    this.listener = listener;
	
	    // all selected by default
	    selected = new boolean[items.size()];
	    for (int i = 0; i < selected.length; i++)
	        selected[i] = true;
	
	    // all text on the spinner
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
	            android.R.layout.simple_spinner_item, new String[] { allText });
	    setAdapter(adapter);
	}
	
	public interface MultiSpinnerListener {
	    public void onItemsSelected(boolean[] selected);
	}
}