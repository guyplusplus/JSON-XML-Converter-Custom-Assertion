package community.layer7.customassertion.xmljsonTransform;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;

import com.geckotechnology.xmljsonconvert.cache.SchemaCache;
import com.l7tech.policy.assertion.ext.AssertionEditor;
import com.l7tech.policy.assertion.ext.AssertionEditorSupport;
import com.l7tech.policy.assertion.ext.EditListener;

/**
 * @author Guy D.
 * October 2018
 */
public class XMLJSONTransformJDialog extends XMLJSONTransformBaseJDialog implements AssertionEditor {

	private static final long serialVersionUID = -1124987349256892481L;
	private static final Logger logger = Logger.getLogger(XMLJSONTransformJDialog.class.getName());
	private static final long JSON_SCHEMA_CACHE_MAX_AGE = 5 * 60 * 1000;
	private static final int JSON_SCHEMA_CACHE_MAX_ENTRIES = 16;
	
	private XMLJSONTransformCustomAssertion xmljsonTransformCustomAssertion;
	private AssertionEditorSupport editorSupport;
	
	static {
		//adjust JSON schema cache for UI
		SchemaCache.setJsonxmlSchemaCacheMaxAge(JSON_SCHEMA_CACHE_MAX_AGE);
		SchemaCache.setJsonxmlSchemaCacheMaxEntries(JSON_SCHEMA_CACHE_MAX_ENTRIES);		
	}
	
	public XMLJSONTransformJDialog(XMLJSONTransformCustomAssertion customAssertion) {
		xmljsonTransformCustomAssertion = customAssertion;
		editorSupport = new AssertionEditorSupport(this);
		//set icon of the JDialog
		Image dialogIconImage = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon.xmljsontransform.32.png"));
		if(dialogIconImage != null)
			super.setIconImage(dialogIconImage);
		//register ESC key
		registerESCKey();
		//set input textfield
		getInputVariableNameJTextField().setText(customAssertion.getInputVariable());
		//set output textfield
		getOutputVariableNameJTextField().setText(customAssertion.getOutputVariable());
		//set combox
		getTransformationJComboBox().setModel(new DefaultComboBoxModel(TransformationHelper.getSupportedTransformations()));
		getTransformationJComboBox().setSelectedIndex(customAssertion.getTransformationTypeID());
		setTestJTextAreaStyles(customAssertion.getTransformationTypeID());
		//set formatted output checkbox
		getFormatOutputJCheckBox().setSelected(customAssertion.isOutputFormatted());
		//set schema
		getSchemaTextArea().setText(customAssertion.getJsonSchema());
		getSchemaTextArea().setCaretPosition(0); //move carret to the start of the text
	}

	@Override
	public void addEditListener(EditListener listener) {
		editorSupport.addListener(listener);
	}

	@Override
	public void edit() {
		setVisible(true);
	}

	@Override
	public void removeEditListener(EditListener listener) {
		editorSupport.removeListener(listener);
	}
	
	@Override
	protected void onCancel() {
		editorSupport.fireCancelled(xmljsonTransformCustomAssertion);
		dispose();
	}
	
	@Override
	protected void onOK() {
		xmljsonTransformCustomAssertion.setInputVariable(super.getInputVariableNameJTextField().getText());
		xmljsonTransformCustomAssertion.setOutputVariable(super.getOutputVariableNameJTextField().getText());
		xmljsonTransformCustomAssertion.setTransformationTypeID(super.getTransformationJComboBox().getSelectedIndex());
		xmljsonTransformCustomAssertion.setOutputFormatted(super.getFormatOutputJCheckBox().isSelected());
		xmljsonTransformCustomAssertion.setJsonSchema(super.getSchemaTextArea().getText());
		editorSupport.fireEditAccepted(xmljsonTransformCustomAssertion);
		dispose();
	}
}