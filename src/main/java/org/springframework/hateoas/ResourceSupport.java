/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.hateoas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.util.Assert;

/**
 * Base class for DTOs to collect links.
 * 
 * @author Oliver Gierke
 */
public class ResourceSupport implements Identifiable<Link> {

	@XmlElement(name = "link", namespace = Link.ATOM_NAMESPACE)
	@org.codehaus.jackson.annotate.JsonProperty("links")
	@com.fasterxml.jackson.annotation.JsonProperty("links")
	private final List<Link> links;

	@org.codehaus.jackson.annotate.JsonIgnore
	@com.fasterxml.jackson.annotation.JsonIgnore
	private final List<EmbeddedResource> embeddedResources;

	public ResourceSupport() {
		this.links = new ArrayList<Link>();
        this.embeddedResources = new ArrayList<EmbeddedResource>();
	}

	/**
	 * Returns the {@link Link} with a rel of {@link Link#REL_SELF}.
	 */
	@org.codehaus.jackson.annotate.JsonIgnore
	@com.fasterxml.jackson.annotation.JsonIgnore
	public Link getId() {
		return getLink(Link.REL_SELF);
	}

	/**
	 * Adds the given link to the resource.
	 * 
	 * @param link
	 */
	public void add(Link link) {
		Assert.notNull(link, "Link must not be null!");
		this.links.add(link);
	}

	/**
	 * Adds all given {@link Link}s to the resource.
	 * 
	 * @param links
	 */
	public void add(Iterable<Link> links) {
		Assert.notNull(links, "Given links must not be null!");
		for (Link candidate : links) {
			add(candidate);
		}
	}

    /**
	 * Embeds the given resource to the resource.
	 *
	 * @param embeddedResource
	 */
	public void embed(EmbeddedResource embeddedResource) {
		Assert.notNull(embeddedResource, "Embedded resource must not be null!");
		this.embeddedResources.add(embeddedResource);
	}

	/**
	 * Embeds all given {@link EmbeddedResource}s to the resource.
	 *
	 * @param links
	 */
	public void embed(Iterable<EmbeddedResource> embeddedResources) {
		Assert.notNull(links, "Embedded resources must not be null!");
		for (EmbeddedResource candidate : embeddedResources) {
			embed(candidate);
		}
	}

	/**
	 * Returns whether the resource contains {@link Link}s at all.
	 * 
	 * @return
	 */
	public boolean hasLinks() {
		return !this.links.isEmpty();
	}

	/**
	 * Returns whether the resource contains a {@link Link} with the given rel.
	 * 
	 * @param rel
	 * @return
	 */
	public boolean hasLink(String rel) {
		return getLink(rel) != null;
	}

	/**
	 * Returns all {@link Link}s contained in this resource.
	 * 
	 * @return
	 */
	public List<Link> getLinks() {
		return Collections.unmodifiableList(links);
	}

	/**
	 * Returns the link with the given rel.
	 * 
	 * @param rel
	 * @return the link with the given rel or {@literal null} if none found.
	 */
	public Link getLink(String rel) {

		for (Link link : links) {
			if (link.getRel().equals(rel)) {
				return link;
			}
		}

		return null;
	}

    /**
	 * Returns whether the resource contains {@link Link}s at all.
	 *
	 * @return
	 */
	public boolean hasEmbeddedResources() {
		return !this.embeddedResources.isEmpty();
	}

	/**
	 * Returns whether the resource contains a {@link Link} with the given rel.
	 *
	 * @param rel
	 * @return
	 */
	public boolean hasEmbeddedResource(String rel) {
		return !getEmbeddedResources(rel).isEmpty();
	}

	/**
	 * Returns all {@link Link}s contained in this resource.
	 *
	 * @return
	 */
	public List<EmbeddedResource> getEmbeddedResources() {
		return Collections.unmodifiableList(embeddedResources);
	}

	/**
	 * Returns the link with the given rel.
	 *
	 * @param rel
	 * @return the link with the given rel or {@literal null} if none found.
	 */
    public List<EmbeddedResource> getEmbeddedResources(String rel) {

        List<EmbeddedResource> relEmbeddedResources = new ArrayList<EmbeddedResource>();

        for (EmbeddedResource embeddedResource : embeddedResources) {
            if (embeddedResource.getRel().equals(rel)) {
                relEmbeddedResources.add(embeddedResource);
            }
        }

        return Collections.unmodifiableList(relEmbeddedResources);
    }

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("embeddedResources: %s, links: %s", embeddedResources.toString(), links.toString());
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!obj.getClass().equals(this.getClass())) {
			return false;
		}

		ResourceSupport that = (ResourceSupport) obj;

		return this.links.equals(that.links);
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.links.hashCode();
	}
}
