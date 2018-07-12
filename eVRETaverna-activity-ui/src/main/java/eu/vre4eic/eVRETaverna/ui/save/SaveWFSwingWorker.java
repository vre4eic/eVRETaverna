package eu.vre4eic.eVRETaverna.ui.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import net.sf.taverna.t2.workbench.ui.impl.Workbench;

import org.apache.log4j.Logger;
//import org.purl.wf4ever.provtaverna.export.Saver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import eu.vre4eic.eVRETaverna.util.Common;

public class SaveWFSwingWorker extends SwingWorker<Boolean, String>{

	private static Logger logger = Logger.getLogger(SaveWFSwingWorker.class);
	//private Saver saver;
	private String bundle;

	//public SaveProvSwingWorker(Saver saver, File bundle){
	public SaveWFSwingWorker( String bundle){
		//this.saver = saver;
		this.bundle = bundle;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		
		
		/*
			
			loginUrl = Common.getLoginUrl();
			
			callBack.partialResults(catalogResults);
			HttpURLConnection loginCon= (HttpURLConnection) loginUrl.openConnection();
			loginCon.setRequestMethod("GET");
			loginCon.setDoInput(true);
			loginCon.setDoOutput(true);
			InputStream iSt= loginCon.getInputStream();
			BufferedReader bRead=new BufferedReader(new InputStreamReader(iSt, "UTF-8"));
			String line="";
		    JsonObject ob=new JsonObject();
			while ((line=bRead.readLine())!=null) {
				ob= (JsonObject) new JsonParser().parse(line); 
				token=ob.get("token").toString();
				System.out.println(token);
			}
			Common.setToken(token.replaceAll("\"", "").trim());
		 */
		String encodedBundle=URLEncoder.encode(bundle, "UTF-8");
		URL wsDescUrl=new URL(Common.getEvreUrl()+"/WorkflowService/wfservice/savewfd?evresid="+Common.getUserId()+"&token="+Common.getToken().replaceAll("\"", "").trim()+"&description="+encodedBundle);
		//URL wsDescUrl=new URL("http://146.48.85.98:8080/wfservice/savewfd?evresid=math&token="+Common.getToken().replaceAll("\"", "").trim()+"&description="+encodedBundle);
		HttpURLConnection descCon= (HttpURLConnection) wsDescUrl.openConnection();
		descCon.setRequestMethod("GET");
		descCon.setDoInput(true);
		descCon.setDoOutput(true);
		InputStream iStDesc= descCon.getInputStream();
		BufferedReader bReadDesc=new BufferedReader(new InputStreamReader(iStDesc, "UTF-8"));
		
		String line="";
		JsonObject ob=new JsonObject();
	    JsonArray jA= new JsonArray();
	    while ((line=bReadDesc.readLine())!=null) {
			ob= (JsonObject) new JsonParser().parse(line); 
			String status=ob.get("status").toString();
			if (status.equalsIgnoreCase("\"SUCCEED\"")) {
				return true;
			}
			
		}
		return false;
	}
	
	@Override
	protected void done() {
		try {
			if (get()) {		
				String msg = "Saved workflow to VRE4EIC catalogue";//:\n" + bundle;		
				logger.info(msg);
				JOptionPane.showMessageDialog(Workbench.getInstance(), 
						msg);
			}
		} catch (CancellationException | InterruptedException  e) {
			logger.warn("Cancelled saving to VRE4EIC");//" + bundle);
		} catch (ExecutionException e) {
			String msg = "Could not save workflow to VRE4EIC: " + e.getCause().getMessage();
			logger.error(msg, e.getCause());
				JOptionPane.showMessageDialog(Workbench.getInstance(), 
						msg);
		}		
	}

}
