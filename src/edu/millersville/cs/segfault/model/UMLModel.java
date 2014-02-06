package edu.millersville.cs.segfault.model;

public class UMLModel {
	
	static final int SET_NAME = 0;
	
	private String modelName;
	
	// Constructors	
	public UMLModel() { 
		modelName = "New UML Model";
	}

	public UMLModel(String serialized) throws Exception {
		this();
		int model_start = serialized.indexOf("<model>" + 7);
		int model_end = serialized.lastIndexOf("</model>");
		if (model_start == 5 || model_end == -1) {
			throw new Exception("Could not find model boundaries.");
		}
		
	}
	
	public UMLModel(UMLModel source) {
		this.modelName = source.getName();
	}
	
	public UMLModel(UMLModel source, int changeType, String newString) throws Exception {
		this(source);
		if (changeType == SET_NAME) {
			this.modelName = newString;
		} else {
			throw new Exception("Invalid change type / parameter to constructor.");
		}
	}
	
	// Observers
	public String serialize() {
		String modelString = "<model>";
		
		modelString = modelString + "<title>" + modelName + "</title>";
		
		modelString += "</model>";
		return modelString;
	}

	public String getName() {
		return this.modelName;
	}

	// Mutators
	public UMLModel changeName(String newName) throws Exception {
		return new UMLModel(this, SET_NAME, newName);
	}
	
}