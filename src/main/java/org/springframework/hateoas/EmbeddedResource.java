package org.springframework.hateoas;

public class EmbeddedResource {
    private ResourceSupport resource;
    private String rel;

    public EmbeddedResource(final ResourceSupport resource, final String rel) {
        this.resource = resource;
        this.rel = rel;
    }

    public ResourceSupport getResource() {
        return resource;
    }

    public String getRel() {
        return rel;
    }

    protected EmbeddedResource() {}
}
