package io.mosip.compliance.toolkit.validators;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.mosip.compliance.toolkit.constants.AppConstants;
import io.mosip.compliance.toolkit.dto.testcases.ValidationInputDto;
import io.mosip.compliance.toolkit.dto.testcases.ValidationResultDto;

@Component
public class ExtractTemplateValidator extends ToolkitValidator {

	public ValidationResultDto validateResponse(ValidationInputDto inputDto) {
		ValidationResultDto validationResultDto = new ValidationResultDto();
		try {
			ObjectNode methodResponse = (ObjectNode) objectMapperConfig.objectMapper()
					.readValue(inputDto.getMethodResponse(), ObjectNode.class);

			JsonNode mainResponse = (JsonNode) methodResponse.get("response");

			int statusCode = Integer.parseInt(mainResponse.get("statusCode").asText());
			if (statusCode >= 200 && statusCode <= 299) {
				validationResultDto.setStatus(AppConstants.SUCCESS);
				validationResultDto.setDescription("Extract Template validation is successful");
				validationResultDto.setDescriptionKey("EXTRACT_TEMPLATE_VALIDATOR_001");
			} else {
				validationResultDto.setStatus(AppConstants.FAILURE);
				validationResultDto.setDescription("Extract Template status code failed, received: " + statusCode);
				validationResultDto.setDescriptionKey("EXTRACT_TEMPLATE_VALIDATOR_002" + AppConstants.ARGUMENTS_DELIMITER + statusCode);
			}
		} catch (Exception e) {
			validationResultDto.setStatus(AppConstants.FAILURE);
			validationResultDto.setDescription(e.getLocalizedMessage());
			return validationResultDto;
		}
		return validationResultDto;
	}
}
