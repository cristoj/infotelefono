package es.webtop.infotelefonoplugin;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;

public class InfoTelefonoPlugin extends CordovaPlugin {
		
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try
		{
			if("GET_IDENTIFICACION".equals(action) == true)
			{
				TelephonyManager manager = (TelephonyManager) super.cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
				String numeroTelefono = manager.getLine1Number();
				String imei = manager.getDeviceId();
				String imsi = manager.getSubscriberId();
				String resultado = 
				" { " +
					" \"numero\" : \"" + numeroTelefono + "\", " +
					" \"imei\" : \"" + imei + "\", " +
					" \"imsi\" : \"" + imsi + "\" " +
				" } ";
				JSONObject resultadoJSON = new JSONObject(resultado);
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, resultadoJSON));
				/*
				 * Si queremos una accion que va a tarda
				 * al final pondriamos un return true y le pasariamos el estado
				 * como una clave data mas del JSON como un sumatorio (i++ cada vez que pase por el el execute)
				 **/
			}
			else
			{
				callbackContext.error("Acción no soportada.");
			}
		}
		catch(RuntimeException exc)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			exc.printStackTrace(pw);
			pw.close();
			String stackTrace = sw.getBuffer().toString();
			callbackContext.error(stackTrace);
		}
		/*-
		 * es false si solo queremos ejecutar una vez
		 * si queremos ejecutar mas veces sería un true y así vulve a empezar el execute
		 * -*/
		return false;
	}
}
