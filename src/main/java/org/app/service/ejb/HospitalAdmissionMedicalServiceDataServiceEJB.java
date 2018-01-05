package org.app.service.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.app.patterns.EntityRepository;
import org.app.patterns.EntityRepositoryBase;
import org.app.service.entities.HospitalAdmission;
import org.app.service.entities.MedicalService;
import org.app.service.entities.MedicalTest;


@Stateless @LocalBean
public class HospitalAdmissionMedicalServiceDataServiceEJB extends EntityRepositoryBase<HospitalAdmission>
implements HospitalAdmissionMedicalServiceDataService, Serializable {
	private static Logger logger = Logger.getLogger(HospitalAdmissionMedicalServiceDataServiceEJB.class.getName());
	
	@EJB // injected DataService
	private MedicalTestDataService medicaltestDataService; 
	// Local component-entity-repository
	private EntityRepository<MedicalService> medicalserviceRepository;
	@PostConstruct
	public void init() {
		// local instantiation of local component-entity-repository
		medicalserviceRepository = new EntityRepositoryBase<MedicalService>(this.em,MedicalService.class);
		logger.info("POSTCONSTRUCT-INIT releaseRepository: " + this.medicalserviceRepository);
		logger.info("POSTCONSTRUCT-INIT featureDataService: " + this.medicaltestDataService);
	}
	
	// Aggregate factory method
		public HospitalAdmission createNewHospitalAdmission(Integer id){
			// create hospital addmission aggregate
			HospitalAdmission hospitaladmission = new HospitalAdmission(id, new Date());
			
			List<MedicalService> medicalserviceHospitalAdmission = new ArrayList<>();
			Date dataPublicare = new Date();
			Long interval =  30l /*zile*/ * 24 /*ore*/ * 60 /*min*/ * 60 /*sec*/ * 1000 /*milisec*/;
			Integer medicalserviceCount = 3;
			for (int i=0; i<=medicalserviceCount-1; i++){
				medicalserviceHospitalAdmission.add(new MedicalService(null, "R-D: " + hospitaladmission.getAddmissionNo() + "." + i, 
						new Date(dataPublicare.getTime() + i * interval), hospitaladmission));
			}
			
			hospitaladmission.setMedicalservice(medicalserviceHospitalAdmission);		
			// save project aggregate
			this.add(hospitaladmission);
			// return project aggregate to service client
			return hospitaladmission;
		}
		
		public MedicalService getMedicalServiceById(Integer serviceId) {
			return medicalserviceRepository.getById(serviceId);
		}

		public String getMessage() {
			return "Hosp.Add.ned.Serv DataService is working...";
		}
}
