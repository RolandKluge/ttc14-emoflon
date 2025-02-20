package org.moflon.tie;

import org.eclipse.emf.ecore.EObject;
import TGGRuntime.CorrespondenceModel;
import org.eclipse.emf.common.util.URI;
import java.io.IOException;
import org.apache.log4j.BasicConfigurator;
import org.moflon.util.IntegratorHelper;
import org.moflon.util.eMoflonEMFUtil;
import SoccerStrategy.SoccerStrategyPackage;
import UpdatePackage.UpdatePackagePackage;
import SoccerPitchPackage.SoccerPitchPackagePackage;


public class SoccerStrategyTrafo extends IntegratorHelper {

	public SoccerStrategyTrafo() throws IOException {
		// Register packages
        eMoflonEMFUtil.init(SoccerStrategyPackage.eINSTANCE);
        eMoflonEMFUtil.init(UpdatePackagePackage.eINSTANCE);
        eMoflonEMFUtil.init(SoccerPitchPackagePackage.eINSTANCE);

                
        // Load rules and set correspondence
		setCorrPackage(SoccerStrategyPackage.eINSTANCE);
		loadRulesFromProject("..");
	}

	public static void main(String[] args) throws IOException {
		// Set up logging
        BasicConfigurator.configure();

		// Forward Transformation
        SoccerStrategyTrafo helper = new SoccerStrategyTrafo();
		helper.performForward("instances/fwd.src.xmi");
		
		// Backward Transformation
		helper = new SoccerStrategyTrafo();
		helper.performBackward("instances/bwd.src.xmi");
	}

	public void performForward(String source) {
		try {
			loadSrc(source);
		} catch (IllegalArgumentException iae) {
			System.err.println("Unable to load " + source + ", " + iae.getMessage());
			return;
		}
		
		integrateForward();
		
		saveTrg("instances/fwd.trg.xmi");
		saveCorr("instances/fwd.corr.xmi");
		saveProtocol("instances/fwd.protocol.xmi");
		
		System.out.println("Completed forward transformation!");
	}

	public void performBackward(String target) {
		try {
			loadTrg(target);
		} catch (IllegalArgumentException iae) {
			System.err.println("Unable to load " + target + ", " + iae.getMessage());
			return;
		}
		
		integrateBackward();
		
		saveSrc("instances/bwd.trg.xmi");
		saveCorr("instances/bwd.corr.xmi");
		saveProtocol("instances/bwd.protocol.xmi");
		
		System.out.println("Completed backward transformation!");
	}
}