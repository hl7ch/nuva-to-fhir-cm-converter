/**
 * 
 */
package ch.hl7.vacd.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ch.hl7.vacd.domain.MigrationItems;

/**
 * 
 */
@Configuration
@ConfigurationProperties(prefix = "application.migrations")
public class MigrationConfiguration {

	List<MigrationItems> items;

	public MigrationConfiguration() {

	}

	public List<MigrationItems> getItems() {
		return items;
	}

	public void setItems(List<MigrationItems> items) {
		this.items = items;
	}
}
