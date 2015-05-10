/*
 * Copyright © 2015 The Authors
 *
 * https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 */
package org.interstellarocean.terraforming.model.refl.selma;

import fr.xebia.extras.selma.Mapper;
import org.interstellarocean.terraforming.TestDomain;
import org.interstellarocean.terraforming.TestDto;

/**
 * @author Dariusz Wakuliński
 */
@Mapper(withIgnoreFields = {"timestamp", "ignoreMe",
		"state", // enum<->string not supported, note no custom mapper is used!
		"domainOnly", "dtoOnly"}) // note selma complains about unhandled fields! +1
public interface TestSelmaToDtoModelTransformer extends SelmaModelTransformer<TestDomain, TestDto> {

	@Override // Required by Selma source code generator: types need to be specified
	TestDto transform(TestDomain source, TestDto destination);

}
